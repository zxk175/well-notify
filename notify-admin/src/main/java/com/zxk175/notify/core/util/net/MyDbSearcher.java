package com.zxk175.notify.core.util.net;

import com.zxk175.notify.core.constant.Const;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.IndexBlock;
import org.lionsoul.ip2region.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author zxk175
 * @see org.lionsoul.ip2region.DbSearcher
 * @since 2019-10-09 15:51
 */
public class MyDbSearcher {

    private DbConfig dbConfig;
    private RandomAccessFile raf = null;
    private long[] headerSip = null;
    private int[] headerPtr = null;
    private int headerLength;

    private long firstIndexPtr = 0;
    private long lastIndexPtr = 0;
    private int totalIndexBlocks = 0;

    private byte[] dbBinStr = null;

    MyDbSearcher(DbConfig dbConfig, String dbFile) throws FileNotFoundException {
        this.dbConfig = dbConfig;
        raf = new RandomAccessFile(dbFile, "r");
    }

    MyDbSearcher(DbConfig dbConfig, byte[] dbBinStr) {
        this.dbConfig = dbConfig;
        this.dbBinStr = dbBinStr;

        firstIndexPtr = Util.getIntLong(dbBinStr, 0);
        lastIndexPtr = Util.getIntLong(dbBinStr, 4);
        totalIndexBlocks = (int) ((lastIndexPtr - firstIndexPtr) / IndexBlock.getIndexBlockLength()) + 1;
    }

    private DataBlock memorySearch(long ip) throws IOException {
        int blockLength = IndexBlock.getIndexBlockLength();
        if (dbBinStr == null) {
            dbBinStr = new byte[(int) raf.length()];
            raf.seek(0L);
            raf.readFully(dbBinStr, 0, dbBinStr.length);

            // initialize the global vars
            firstIndexPtr = Util.getIntLong(dbBinStr, 0);
            lastIndexPtr = Util.getIntLong(dbBinStr, 4);
            totalIndexBlocks = (int) ((lastIndexPtr - firstIndexPtr) / blockLength) + 1;
        }

        // search the index blocks to define the data
        int l = 0, h = totalIndexBlocks;
        long sip, eip, dataptr = 0;
        while (l <= h) {
            int m = (l + h) >> 1;
            int p = (int) (firstIndexPtr + m * blockLength);

            sip = Util.getIntLong(dbBinStr, p);
            if (ip < sip) {
                h = m - 1;
            } else {
                eip = Util.getIntLong(dbBinStr, p + 4);
                if (ip > eip) {
                    l = m + 1;
                } else {
                    dataptr = Util.getIntLong(dbBinStr, p + 8);
                    break;
                }
            }
        }

        // not matched
        if (dataptr == 0) {
            return null;
        }

        // get the data
        int dataLen = (int) ((dataptr >> 24) & 0xFF);
        int dataPtr = (int) ((dataptr & 0x00FFFFFF));
        int cityId = (int) Util.getIntLong(dbBinStr, dataPtr);
        String region = new String(dbBinStr, dataPtr + 4, dataLen - 4, Const.UTF_8_OBJ);

        return new DataBlock(cityId, region, dataPtr);
    }

    DataBlock memorySearch(String ip) throws IOException {
        return memorySearch(Util.ip2long(ip));
    }

    private DataBlock getByIndexPtr(long ptr) throws IOException {
        raf.seek(ptr);
        byte[] buffer = new byte[12];
        raf.readFully(buffer, 0, buffer.length);
        long extra = Util.getIntLong(buffer, 8);

        int dataLen = (int) ((extra >> 24) & 0xFF);
        int dataPtr = (int) ((extra & 0x00FFFFFF));

        raf.seek(dataPtr);
        byte[] data = new byte[dataLen];
        raf.readFully(data, 0, data.length);

        int cityId = (int) Util.getIntLong(data, 0);
        String region = new String(data, 4, data.length - 4, Const.UTF_8_OBJ);

        return new DataBlock(cityId, region, dataPtr);
    }

    public DataBlock btreeSearch(String ip) throws IOException {
        return btreeSearch(Util.ip2long(ip));
    }

    private DataBlock btreeSearch(long ip) throws IOException {
        // check and load the header
        if (headerSip == null) {
            // pass the super block
            raf.seek(8L);
            byte[] b = new byte[dbConfig.getTotalHeaderSize()];
            raf.readFully(b, 0, b.length);

            // fill the header
            // b.length / 8
            int len = b.length >> 3, idx = 0;
            headerSip = new long[len];
            headerPtr = new int[len];
            long startIp, dataPtr;
            for (int i = 0; i < b.length; i += 8) {
                startIp = Util.getIntLong(b, i);
                dataPtr = Util.getIntLong(b, i + 4);
                if (dataPtr == 0) {
                    break;
                }

                headerSip[idx] = startIp;
                headerPtr[idx] = (int) dataPtr;
                idx++;
            }

            headerLength = idx;
        }

        // 1. define the index block with the binary search
        if (ip == headerSip[0]) {
            return getByIndexPtr(headerPtr[0]);
        } else if (ip == headerSip[headerLength - 1]) {
            return getByIndexPtr(headerPtr[headerLength - 1]);
        }

        int l = 0, h = headerLength, sptr = 0, eptr = 0;
        while (l <= h) {
            int m = (l + h) >> 1;

            // perfect matched, just return it
            if (ip == headerSip[m]) {
                if (m > 0) {
                    sptr = headerPtr[m - 1];
                    eptr = headerPtr[m];
                } else {
                    sptr = headerPtr[m];
                    eptr = headerPtr[m + 1];
                }

                break;
            }

            // less then the middle value
            if (ip < headerSip[m]) {
                if (m == 0) {
                    sptr = headerPtr[m];
                    eptr = headerPtr[m + 1];
                    break;
                } else if (ip > headerSip[m - 1]) {
                    sptr = headerPtr[m - 1];
                    eptr = headerPtr[m];
                    break;
                }
                h = m - 1;
            } else {
                if (m == headerLength - 1) {
                    sptr = headerPtr[m - 1];
                    eptr = headerPtr[m];
                    break;
                } else if (ip <= headerSip[m + 1]) {
                    sptr = headerPtr[m];
                    eptr = headerPtr[m + 1];
                    break;
                }
                l = m + 1;
            }
        }

        // match nothing just stop it
        if (sptr == 0) {
            return null;
        }

        // 2. search the index blocks to define the data
        int blockLen = eptr - sptr, blen = IndexBlock.getIndexBlockLength();
        // include the right border block
        byte[] iBuffer = new byte[blockLen + blen];
        raf.seek(sptr);
        raf.readFully(iBuffer, 0, iBuffer.length);

        l = 0;
        h = blockLen / blen;
        long sip, eip, dataptr = 0;
        while (l <= h) {
            int m = (l + h) >> 1;
            int p = m * blen;
            sip = Util.getIntLong(iBuffer, p);
            if (ip < sip) {
                h = m - 1;
            } else {
                eip = Util.getIntLong(iBuffer, p + 4);
                if (ip > eip) {
                    l = m + 1;
                } else {
                    dataptr = Util.getIntLong(iBuffer, p + 8);
                    break;
                }
            }
        }

        // not matched
        if (dataptr == 0) {
            return null;
        }

        // 3. get the data
        int dataLen = (int) ((dataptr >> 24) & 0xFF);
        int dataPtr = (int) ((dataptr & 0x00FFFFFF));

        raf.seek(dataPtr);
        byte[] data = new byte[dataLen];
        raf.readFully(data, 0, data.length);

        int cityId = (int) Util.getIntLong(data, 0);
        String region = new String(data, 4, data.length - 4, Const.UTF_8_OBJ);

        return new DataBlock(cityId, region, dataPtr);
    }

    public DataBlock binarySearch(String ip) throws IOException {
        return binarySearch(Util.ip2long(ip));
    }

    private DataBlock binarySearch(long ip) throws IOException {
        int blockLength = IndexBlock.getIndexBlockLength();
        if (totalIndexBlocks == 0) {
            raf.seek(0L);
            byte[] superBytes = new byte[8];
            raf.readFully(superBytes, 0, superBytes.length);
            // initialize the global vars
            firstIndexPtr = Util.getIntLong(superBytes, 0);
            lastIndexPtr = Util.getIntLong(superBytes, 4);
            totalIndexBlocks = (int) ((lastIndexPtr - firstIndexPtr) / blockLength) + 1;
        }

        // search the index blocks to define the data
        int l = 0, h = totalIndexBlocks;
        byte[] buffer = new byte[blockLength];
        long sip, eip, dataptr = 0;
        while (l <= h) {
            int m = (l + h) >> 1;
            // set the file pointer
            raf.seek(firstIndexPtr + m * blockLength);
            raf.readFully(buffer, 0, buffer.length);
            sip = Util.getIntLong(buffer, 0);
            if (ip < sip) {
                h = m - 1;
            } else {
                eip = Util.getIntLong(buffer, 4);
                if (ip > eip) {
                    l = m + 1;
                } else {
                    dataptr = Util.getIntLong(buffer, 8);
                    break;
                }
            }
        }

        // not matched
        if (dataptr == 0) {
            return null;
        }

        // get the data
        int dataLen = (int) ((dataptr >> 24) & 0xFF);
        int dataPtr = (int) ((dataptr & 0x00FFFFFF));

        raf.seek(dataPtr);
        byte[] data = new byte[dataLen];
        raf.readFully(data, 0, data.length);

        int cityId = (int) Util.getIntLong(data, 0);
        String region = new String(data, 4, data.length - 4, Const.UTF_8_OBJ);

        return new DataBlock(cityId, region, dataPtr);
    }

    public void close() throws IOException {
        headerSip = null;
        headerPtr = null;
        dbBinStr = null;
        if (raf != null) {
            raf.close();
        }
    }
}

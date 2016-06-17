package com.britesky.payanywhere.ui.pic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

/**
 * 打印机辅助
 * 
 * @author nsz        2015年1月30日
 */
public class PrintUtil {
        final static int BUFFER_SIZE = 4096;

        /**
         * 对一个byte[] 进行打印
         * @param printText
         * @return
         * add by yidie
         */
        
        public static boolean printBytes(byte[] printText,OutputStream mOutputStream) {
                boolean returnValue = true;
                try {
                        mOutputStream.write(printText);
                } catch (Exception ex) {
                        returnValue = false;
                }
                return returnValue;
        }

        /**
         * "\n" 就是换行
         * @param paramString
         * @return
         * add by yidie
         */
        public static boolean printString(String paramString,OutputStream mOutputStream) {
                return printBytes(getGbk(paramString),mOutputStream);
        }

        /***************************************************************************
         * add by yidie 2012-01-10 功能：设置打印绝对位置 
         * 参数： 
         * int 在当前行，定位光标位置，取值范围0至576点 
         * 说明：
         * 在字体常规大小下，每汉字24点，英文字符12点 如位于第n个汉字后，则position=24*n
         * 如位于第n个半角字符后，则position=12*n
         ****************************************************************************/

        public static byte[] setCusorPosition(int position) {
                byte[] returnText = new byte[4]; // 当前行，设置绝对打印位置 ESC $ bL bH
                returnText[0] = 0x1B;
                returnText[1] = 0x24;
                returnText[2] = (byte) (position % 256);
                returnText[3] = (byte) (position / 256);
                return returnText;
        }

        /**
         * 设置打印机的行高
         * @param h
         * @return
         */
        public static byte[] setLineHeight(byte h) {
                byte[] returnText = new byte[] { 0x1B, 0x33, h }; // 切纸； 1B 33 n
                return returnText;
        }

        public static byte[] setDefaultLineHeight() {
                byte[] returnText = new byte[] { 0x1B, 0x32 }; // 切纸； 1B 32
                return returnText;
        }

        public static byte[] InputStreamTOByte(InputStream in) throws IOException {

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] data = new byte[BUFFER_SIZE];
                int count = -1;
                while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
                        outStream.write(data, 0, count);

                data = null;
                return outStream.toByteArray();
        }
        
        /**
         * 打印我有外卖的logo
         * @param context
         */
        public static void printLogo(Context context,OutputStream mOutputStream) {
                PrintUtil.printBytes(PrintUtil.setLineHeight((byte) 0),mOutputStream);
                byte[] b;
                try {
                		InputStream is = context.getAssets().open("bill.bin");//getResourceAsStream("/assets/bill.bin");
                        b = InputStreamTOByte(is);
                        PrintUtil.printBytes(b,mOutputStream);
                        PrintUtil.printBytes(PrintUtil.setDefaultLineHeight(),mOutputStream);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

         public static byte[] getLogo(Context c) {
                InputStream is = c.getClass().getResourceAsStream("/assets/bill.bin");
                byte[] b;
                try {
                        b = InputStreamTOByte(is);
                        return b;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
         }
         
         /**
          * 得到店铺logo
          * @param c
          * @param bit
          * @return
          */
        public static byte[] getLogo(Context c, byte[] bit) {
                InputStream is = c.getClass().getResourceAsStream("/assets/bill.bin");
                byte[] b = bit;
                try {
                        b = InputStreamTOByte(is);
                        return b;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }
        
        public static byte[] getLogo(byte[] bs) {
                byte[] b;
                try {
                        b = bs;
                        return b;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }        
        
        /**
         * 支付打印(二维码)
         * @param b
         * @param money
         * @return
         * [url=home.php?mod=space&uid=2643633]@throws[/url] InvalidParameterException
         * @throws SecurityException
         * @throws IOException
         */
        public static boolean printAlipayTitle(byte[] bytes, String money,OutputStream mOutputStream) 
                        throws InvalidParameterException, SecurityException, IOException {
                
                int iNum = 0;
                byte[] tempBuffer = new byte[1000];
                if(mOutputStream==null)
                	return false;
                
                
                byte[]  oldText = setAlignCenter('2');
                System.arraycopy(oldText, 0,  tempBuffer,  iNum,  oldText.length);
                iNum += oldText.length;
                oldText = setWH('4'); 
                System.arraycopy(oldText, 0, tempBuffer,  iNum,  oldText.length);
                iNum += oldText.length;
                oldText = setBold(true);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                oldText = getGbk("支付凭证\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                
                oldText = getGbk("\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                
                oldText = setAlignCenter('2');
                System.arraycopy(oldText, 0,  tempBuffer,  iNum,  oldText.length);
                iNum += oldText.length;
                oldText = setWH('3'); 
                System.arraycopy(oldText, 0, tempBuffer,  iNum,  oldText.length);
                iNum += oldText.length;
                oldText = setBold(true);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                oldText = getGbk("您共消费了" + money + "元\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                
                oldText = getGbk("\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                
                oldText = setAlignCenter('2');
                System.arraycopy(oldText, 0,  tempBuffer,  iNum,  oldText.length);
                iNum += oldText.length;
                oldText = setWH('3'); 
                System.arraycopy(oldText, 0, tempBuffer,  iNum,  oldText.length);
                iNum += oldText.length;
                oldText = setBold(true);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                oldText = getGbk("请扫码支付\n\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
//                oldText = setWH('1'); 
//                System.arraycopy(oldText, 0, tempBuffer,  iNum,  oldText.length);
//                iNum += oldText.length;

                try {
                        mOutputStream.write(tempBuffer);
                        printBytes(bytes,mOutputStream);
                        printString("\n\n\n",mOutputStream);
                        printBytes(CutPaper(),mOutputStream);
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }
                return true;
        }

        /***************************************************************************
         * add by yidie 2012-01-10 功能：订单打印 参数： String 订单短号 OrderDetail 打印内容，包含
         * GoodsInfo[] String 打印标题
         ****************************************************************************/

        public static boolean printOrder(Context c, byte[] b,OutputStream mOutputStream)
                        throws InvalidParameterException, SecurityException, IOException {

                DecimalFormat dcmFmt = new DecimalFormat("0.00");
                int iNum = 0, i;

                byte[] tempBuffer = new byte[8000];
                String stTmp = "";

                if(mOutputStream==null)
                	return false;
                
                byte[] oldText = setAlignCenter('2');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getLogo(c, b);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setAlignCenter('2');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('4');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setBold(true);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setAlignCenter('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setCusorPosition(324);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                String strTime = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                                Locale.SIMPLIFIED_CHINESE).format(new Date());
                oldText = getGbk(strTime + "打印\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setBold(false);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("----------------------------------------------\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('3');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("   商品名称              单价    数量    金额\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("----------------------------------------------\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('3');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setAlignCenter('2');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("\n感谢使用[我有外卖]订餐,24小时服务热线 4008519517\n\n\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = CutPaper();
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                
//                SerialPort mSerialPort = getSerialPort();
//                OutputStream mOutputStream = mSerialPort.getOutputStream();
                try {
                        mOutputStream.write(tempBuffer);
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }
                return true;
        }

        /***************************************************************************
         * add by yidie 2012-01-12 功能：报表打印 参数： String 打印标题，如“月报表：2013-01”
         * ReportUserSale 打印内容，包含 UserSaleInfo[]
         ****************************************************************************/

        public static boolean printReportUser(OutputStream mOutputStream) throws InvalidParameterException,
                        SecurityException, IOException {

                int iNum = 0;
                String stTmp = "";

                byte[] tempBuffer = new byte[8000];
//                SerialPort mSerialPort = getSerialPort();
//                OutputStream mOutputStream = mSerialPort.getOutputStream();

                if(mOutputStream==null)
                	return false;
                
                byte[] oldText = setAlignCenter('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('3');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setCusorPosition(324);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                String strTime = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                                Locale.SIMPLIFIED_CHINESE).format(new Date());
                oldText = getGbk(strTime + "打印\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setAlignCenter('2');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('4');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setBold(true);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("\n\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setAlignCenter('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setBold(false);
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = setWH('1');
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("　　   用户  　　　　　　   售出数量　售出金额\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = getGbk("----------------------------------------------\n");
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;

                oldText = CutPaper();
                System.arraycopy(oldText, 0, tempBuffer, iNum, oldText.length);
                iNum += oldText.length;
                try {
                        mOutputStream.write(tempBuffer);
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }

                return true;
        }

        /***************************************************************************
         * add by yidie 2012-01-12 功能：报表打印 参数： String 打印标题，如“月报表：2013-01” ReportSale
         * 打印内容，包含 SaleInfo[]
         ****************************************************************************/

//        private static SerialPort mSerialPort = null;

//        public static SerialPort getSerialPort() throws SecurityException,
//                        IOException, InvalidParameterException {
//                if (mSerialPort == null) {
//                        String spFile = null;
//                        String model = MainBoardUtil.getModel(); // android.os.Build.MODEL.toLowerCase();
//                        if (model.contains(Constants.MAIN_BOARD_SMDKV210)) {
//                                spFile = "/dev/s3c2410_serial0";
//                        } else if (model.contains(Constants.MAIN_BOARD_RK30)) {
//                                spFile = "/dev/ttyS1";
//                        } else if (model.contains(Constants.MAIN_BOARD_C500)) {
//                                spFile = "/dev/ttyS1";
//                        } else {
//                                throw new IOException("unknow hardware!");
//                        }
//
//                        int baudrate = 115200;
//                        boolean flagCon = true;
//
//                        File myFile = new File(spFile);
//
//                        /* Open the serial port */
//                        mSerialPort = new SerialPort(myFile, baudrate, 0, flagCon);
//                }
//                return mSerialPort;
//        }

//        public static void closeSerialPort() {
//                if (mSerialPort != null) {
//                        mSerialPort.close();
//                        mSerialPort = null;
//                }
//        }

        public static byte[] getGbk(String stText) {
                byte[] returnText = null;
                try {
                        returnText = stText.getBytes("GBK"); // 必须放在try内才可以
                } catch (Exception ex) {
                        ;
                }
                return returnText;
        }

        public static byte[] setWH(char dist) {
                byte[] returnText = new byte[3]; // GS ! 11H 倍宽倍高
                returnText[0] = 0x1D;
                returnText[1] = 0x21;

                switch (dist) // 1-无；2-倍宽；3-倍高； 4-倍宽倍高
                {
                case '2':
                        returnText[2] = 0x10;
                        break;
                case '3':
                        returnText[2] = 0x01;
                        break;
                case '4':
                        returnText[2] = 0x11;
                        break;
                default:
                        returnText[2] = 0x00;
                        break;
                }

                return returnText;
        }

        /**
         * 打印的对齐方式
         * @param dist
         * @return
         */
        public static byte[] setAlignCenter(char dist) {
                byte[] returnText = new byte[3]; // 对齐 ESC a
                returnText[0] = 0x1B;
                returnText[1] = 0x61;

                switch (dist) // 1-左对齐；2-居中对齐；3-右对齐
                {
                case '2':
                        returnText[2] = 0x01;
                        break;
                case '3':
                        returnText[2] = 0x02;
                        break;
                default:
                        returnText[2] = 0x00;
                        break;
                }
                return returnText;
        }

        public static byte[] setBold(boolean dist) {
                byte[] returnText = new byte[3]; // 加粗 ESC E
                returnText[0] = 0x1B;
                returnText[1] = 0x45;

                if (dist) {
                        returnText[2] = 0x01; // 表示加粗
                } else {
                        returnText[2] = 0x00;
                }
                return returnText;
        }

        public static byte[] PrintBarcode(String stBarcode) {
                int iLength = stBarcode.length() + 4;
                byte[] returnText = new byte[iLength];

                returnText[0] = 0x1D;
                returnText[1] = 'k';
                returnText[2] = 0x45;
                returnText[3] = (byte) stBarcode.length(); // 条码长度；

                System.arraycopy(stBarcode.getBytes(), 0, returnText, 4,
                                stBarcode.getBytes().length);

                return returnText;
        }

        /**
         * 切纸
         * @return
         */
        public static byte[] CutPaper() {
                byte[] returnText = new byte[] { 0x1D, 0x56, 0x42, 0x00 }; // 切纸； GS V
                                                                                                                                        // 66D 0D
                return returnText;
        }
}

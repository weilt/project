package com.hwt.domain.mapper.admin;
//
//import java.io.File;
//import java.io.FileInputStream;
//
//        public class ReadMP3 {
//            try {
//                String mp3path= "mmt/sdcard2/Music/大艺术家.mp3";
//                MP3File f = new MP3File(mp3Path);
//                MP3AudioHeader header= (MP3AudioHeader)f.getAudioHeader(); //获得头部信息
//                System.out.println(header.getTrackLength()); //获得歌曲时长
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        //    上面的方法是对MP3头部信息进行读取，接下来对MP3的歌名，歌手进行读取
//        try {
//                MP3File file = new MP3File("mmt/sdcard2/Music/大艺术家.mp3");
//
//
//                String songName=file.getID3v2Tag().frameMap.get("TIT2").toString();
//                String singer=file.getID3v2Tag().frameMap.get("TPE1").toString();
//                String author=file.getID3v2Tag().frameMap.get("TALB").toString();
//                System.out.println(new String(songName.getBytes("ISO-8859-1"),"GB2312"));
//                System.out.println(new String(singer.getBytes("ISO-8859-1"),"GB2312"));
//                System.out.println(new String(author.getBytes("ISO-8859-1"),"GB2312"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (TagException e) {
//                e.printStackTrace();
//            } catch(ReadOnlyFileException e) {
//                e.printStackTrace();
//            } catch (InvalidAudioFrameException e) {
//                e.printStackTrace();
//            }
//
//        //    这样输出的格式为：
//        //    Text="大艺术家"; 
//        //    Text="蔡依林"; 
//        //    Text="MUSE"; 
//        //
//        //    修改一下代码：
//
//            String songName=new String(file.getID3v2Tag().frameMap.get("TIT2").toString().getBytes("ISO-8859-1"),"GB2312");
//            String singer=new String(file.getID3v2Tag().frameMap.get("TPE1").toString().getBytes("ISO-8859-1"),"GB2312");
//            String author=new String(file.getID3v2Tag().frameMap.get("TALB").toString().getBytes("ISO-8859-1"),"GB2312");
//                    System.out.println(songName.substring(6, songName.length()-3));
//                    System.out.println(singer.substring(6, songName.length()-3));
//                    System.out.println(author.substring(6, songName.length()-3));
//        }
//
//
///**
//
//* @param args
//
//* @throws Exception 
//
//*/
//
//public static void main(String[] args) throws Exception {
//
//// TODO Auto-generated method stub
//
//
//String path = "D:\\CloudMusic\\MyT1aN - 船票.mp3";
//
//
//readMp3ID3V1(path);
//
//
//}
//
//
//
//public static void readMp3ID3V1(String path) throws  Exception{
//
// byte[] buf = new byte[1024];
//File file = new File(path);
//
//FileInputStream fis = new FileInputStream(file);
//
///*---读取MP3文件尾部信息，并显示----*/
//
//long size = file.length();
//
//System.out.println("文件总字节数："+size);
//
//
//
//fis.skip(size-128);
//
//
//
////标志位TAG:3  byte 
//
//fis.read(buf,0,3);
//
//String tag = new String(buf,0,3);
//System.out.println( "ID3V1:"+tag);
//
//
//
////歌曲名称 30 byte 
//
//fis.read(buf,0,30);
//
//String songname = new String(buf,0,30);
//
//System.out.println( "song name:"+songname);
//
//
//
////歌手名称   30   byte 
//
//int len = fis.read(buf,0,30);
//
//String songername = new String(buf,0,len);
//
//System.out.println( "songer name:"+songername);
//
//
//
////专辑名称   30   byte 
//len = fis.read(buf,0,30);
//
//String albumname = new String(buf,0,len);
//
//System.out.println( "album name:"+albumname);
//
//
//
////年代 4 byte 
//
//fis.read(buf,0,4);
//
//String year = new String(buf,0,4);
//
//System.out.println( "year : "+year);
//
//
//
////comment 30 byte 
//
//fis.read(buf,0,28);
//
//len = fis.read(buf,0,28);
//
//String con = new String(buf,0,len);
//
//System.out.println( "comment: "+con);
//
////genre   1   byte 
//
//fis.read(buf,0,1);
//
//System.out.println( "Genre: "+buf[0]);
//
//fis.close();
//
//
//
//}
//
//
//
//
//}

//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.io.UnsupportedEncodingException;
///** * 获得MP3文件的信息 * */
//public class ReadMP3{
//        public static void main(String[] args) {
//    //TODO 演示
//            File MP3FILE = new File("test.mp3");
//            try {
//                MP3Info info = new MP3Info(MP3FILE);
//                info.setCharset("gbk");
//                System.out.println(info.getSongName());
//                System.out.println(info.getArtist());
//                System.out.println(info.getAlbum());
//                System.out.println(info.getYear());
//                System.out.println(info.getComment());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        private String charset = "utf-8";
//        // 解析MP3信息时用的字符编码 private byte[] buf;
//    // MP3的标签信息的byte数组
//    // /** *
//    // 实例化一个获得MP3文件的信息的类 * * @param mp3 *MP3文件 * @throws IOException * 读取MP3出错或则MP3文件不存在 */
//    public MP3Info(File mp3) throws IOException
//    { buf = new byte[128];
//    // 初始化标签信息的byte数组\
//        RandomAccessFile raf = new RandomAccessFile(mp3, "r");
//        // 随机读写方式打开MP3文件
//        raf.seek(raf.length() - 128);
//        // 移动到文件MP3末尾
//        raf.read(buf);
//        // 读取标签信息
//        raf.close();
//        // 关闭文件
//        if (buf.length != 128) {
//            // 数据是否合法
//            throw new IOException("MP3标签信息数据长度不合法!");
//        } if (!"TAG".equalsIgnoreCase(new String(buf, 0, 3))) {
//            // 信息格式是否正确
//        throw new IOException("MP3标签信息数据格式不正确!"); }
//    }
//    /** * 获得目前解析时用的字符编码 * * @return 目前解析时用的字符编码 */
//    public String getCharset() { return charset; }
//    /** * 设置解析时用的字符编码 * * @param charset *解析时用的字符编码 */
//    public void setCharset(String charset) { this.charset = charset; }
//    public String getSongName() {
//        try {
//            return new String(buf, 3, 30, charset).trim();
//        } catch (UnsupportedEncodingException e) {
//            return new String(buf, 3, 30).trim(); }
//    }
//    public String getArtist() {
//        try {
//            return new String(buf, 33, 30, charset).trim();
//        } catch (UnsupportedEncodingException e) {
//            return new String(buf, 33, 30).trim();
//        }
//    }
//    public String getAlbum() {
//        try {
//        return new String(buf, 63, 30, charset).trim();
//        } catch (UnsupportedEncodingException e) {
//            return new String(buf, 63, 30).trim();
//        }
//    }
//    public String getYear() {
//        try {
//            return new String(buf, 93, 4, charset).trim();
//        } catch (UnsupportedEncodingException e) {
//            return new String(buf, 93, 4).trim();
//        }
//    }
//    public String getComment() {
//        try {
//            return new String(buf, 97, 28, charset).trim();
//        } catch (UnsupportedEncodingException e) {
//            return new String(buf, 97, 28).trim();
//        }
//    }
//}

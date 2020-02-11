package com.dbpower.urlimageviewhelper;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DiskLruCache implements Closeable {
   static final long ANY_SEQUENCE_NUMBER = -1L;
   private static final String CLEAN = "CLEAN";
   private static final String DIRTY = "DIRTY";
   static final String JOURNAL_FILE = "journal";
   static final String JOURNAL_FILE_TMP = "journal.tmp";
   static final String MAGIC = "libcore.io.DiskLruCache";
   private static final String READ = "READ";
   private static final String REMOVE = "REMOVE";
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   static final String VERSION_1 = "1";
   private final int appVersion;
   private final Callable cleanupCallable;
   private final File directory;
   private final ExecutorService executorService;
   private final File journalFile;
   private final File journalFileTmp;
   private Writer journalWriter;
   private final LinkedHashMap lruEntries = new LinkedHashMap(0, 0.75F, true);
   private final long maxSize;
   private long nextSequenceNumber = 0L;
   private int redundantOpCount;
   private long size = 0L;
   private final int valueCount;

   private DiskLruCache(File var1, int var2, int var3, long var4) {
      this.executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
      this.cleanupCallable = new Callable() {
         public Void call() throws Exception {
            DiskLruCache var1 = DiskLruCache.this;
            synchronized(var1){}

            Throwable var10000;
            boolean var10001;
            label197: {
               try {
                  if (DiskLruCache.this.journalWriter == null) {
                     return null;
                  }
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label197;
               }

               try {
                  DiskLruCache.this.trimToSize();
                  if (DiskLruCache.this.journalRebuildRequired()) {
                     DiskLruCache.this.rebuildJournal();
                     DiskLruCache.this.redundantOpCount = 0;
                  }
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label197;
               }

               label187:
               try {
                  return null;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label187;
               }
            }

            while(true) {
               Throwable var2 = var10000;

               try {
                  throw var2;
               } catch (Throwable var19) {
                  var10000 = var19;
                  var10001 = false;
                  continue;
               }
            }
         }
      };
      this.directory = var1;
      this.appVersion = var2;
      this.journalFile = new File(var1, "journal");
      this.journalFileTmp = new File(var1, "journal.tmp");
      this.valueCount = var3;
      this.maxSize = var4;
   }

   private void checkNotClosed() {
      if (this.journalWriter == null) {
         throw new IllegalStateException("cache is closed");
      }
   }

   private static void closeQuietly(Closeable var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (RuntimeException var1) {
            throw var1;
         } catch (Exception var2) {
         }
      }

   }

   private void completeEdit(DiskLruCache.Editor var1, boolean var2) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label1539: {
         DiskLruCache.Entry var3;
         boolean var10001;
         IllegalStateException var167;
         try {
            var3 = var1.entry;
            if (var3.currentEditor != var1) {
               var167 = new IllegalStateException();
               throw var167;
            }
         } catch (Throwable var162) {
            var10000 = var162;
            var10001 = false;
            break label1539;
         }

         byte var4 = 0;
         int var5 = var4;
         if (var2) {
            label1535: {
               var5 = var4;

               try {
                  if (var3.readable) {
                     break label1535;
                  }
               } catch (Throwable var164) {
                  var10000 = var164;
                  var10001 = false;
                  break label1539;
               }

               var5 = 0;

               while(true) {
                  try {
                     if (var5 >= this.valueCount) {
                        break;
                     }
                  } catch (Throwable var166) {
                     var10000 = var166;
                     var10001 = false;
                     break label1539;
                  }

                  try {
                     if (!var3.getDirtyFile(var5).exists()) {
                        var1.abort();
                        StringBuilder var171 = new StringBuilder("edit didn't create file ");
                        var171.append(var5);
                        var167 = new IllegalStateException(var171.toString());
                        throw var167;
                     }
                  } catch (Throwable var165) {
                     var10000 = var165;
                     var10001 = false;
                     break label1539;
                  }

                  ++var5;
               }

               var5 = var4;
            }
         }

         while(true) {
            long var7;
            label1499: {
               Writer var6;
               StringBuilder var168;
               label1498: {
                  try {
                     if (var5 < this.valueCount) {
                        break label1499;
                     }

                     ++this.redundantOpCount;
                     var3.currentEditor = null;
                     if (!(var3.readable | var2)) {
                        break label1498;
                     }

                     var3.readable = true;
                     var6 = this.journalWriter;
                     var168 = new StringBuilder("CLEAN ");
                     var168.append(var3.key);
                     var168.append(var3.getLengths());
                     var168.append('\n');
                     var6.write(var168.toString());
                  } catch (Throwable var163) {
                     var10000 = var163;
                     var10001 = false;
                     break label1539;
                  }

                  if (var2) {
                     try {
                        var7 = (long)(this.nextSequenceNumber++);
                        var3.sequenceNumber = var7;
                     } catch (Throwable var158) {
                        var10000 = var158;
                        var10001 = false;
                        break label1539;
                     }
                  }
                  break;
               }

               try {
                  this.lruEntries.remove(var3.key);
                  var6 = this.journalWriter;
                  var168 = new StringBuilder("REMOVE ");
                  var168.append(var3.key);
                  var168.append('\n');
                  var6.write(var168.toString());
                  break;
               } catch (Throwable var157) {
                  var10000 = var157;
                  var10001 = false;
                  break label1539;
               }
            }

            File var172;
            try {
               var172 = var3.getDirtyFile(var5);
            } catch (Throwable var161) {
               var10000 = var161;
               var10001 = false;
               break label1539;
            }

            if (var2) {
               try {
                  if (var172.exists()) {
                     File var169 = var3.getCleanFile(var5);
                     var172.renameTo(var169);
                     var7 = var3.lengths[var5];
                     long var9 = var169.length();
                     var3.lengths[var5] = var9;
                     this.size = this.size - var7 + var9;
                  }
               } catch (Throwable var159) {
                  var10000 = var159;
                  var10001 = false;
                  break label1539;
               }
            } else {
               try {
                  deleteIfExists(var172);
               } catch (Throwable var160) {
                  var10000 = var160;
                  var10001 = false;
                  break label1539;
               }
            }

            ++var5;
         }

         try {
            if (this.size <= this.maxSize && !this.journalRebuildRequired()) {
               return;
            }
         } catch (Throwable var156) {
            var10000 = var156;
            var10001 = false;
            break label1539;
         }

         try {
            this.executorService.submit(this.cleanupCallable);
         } catch (Throwable var155) {
            var10000 = var155;
            var10001 = false;
            break label1539;
         }

         return;
      }

      Throwable var170 = var10000;
      try {
         throw var170;
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }
   }

   private static Object[] copyOfRange(Object[] var0, int var1, int var2) {
      int var3 = var0.length;
      if (var1 > var2) {
         throw new IllegalArgumentException();
      } else if (var1 >= 0 && var1 <= var3) {
         var2 -= var1;
         var3 = Math.min(var2, var3 - var1);
         Object[] var4 = (Object[])Array.newInstance(var0.getClass().getComponentType(), var2);
         System.arraycopy(var0, var1, var4, 0, var3);
         return var4;
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   private static void deleteContents(File var0) throws IOException {
      File[] var1 = var0.listFiles();
      StringBuilder var4;
      if (var1 == null) {
         var4 = new StringBuilder("not a directory: ");
         var4.append(var0);
         throw new IllegalArgumentException(var4.toString());
      } else {
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            var0 = var1[var3];
            if (var0.isDirectory()) {
               deleteContents(var0);
            }

            if (!var0.delete()) {
               var4 = new StringBuilder("failed to delete file: ");
               var4.append(var0);
               throw new IOException(var4.toString());
            }
         }

      }
   }

   private static void deleteIfExists(File var0) throws IOException {
      if (var0.exists() && !var0.delete()) {
         throw new IOException();
      }
   }

   private DiskLruCache.Editor edit(String var1, long var2) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label338: {
         DiskLruCache.Entry var4;
         boolean var10001;
         try {
            this.checkNotClosed();
            this.validateKey(var1);
            var4 = (DiskLruCache.Entry)this.lruEntries.get(var1);
         } catch (Throwable var38) {
            var10000 = var38;
            var10001 = false;
            break label338;
         }

         if (var2 != -1L) {
            if (var4 == null) {
               return null;
            }

            long var5;
            try {
               var5 = var4.sequenceNumber;
            } catch (Throwable var37) {
               var10000 = var37;
               var10001 = false;
               break label338;
            }

            if (var5 != var2) {
               return null;
            }
         }

         DiskLruCache.Editor var7;
         if (var4 == null) {
            try {
               var4 = new DiskLruCache.Entry(var1, (DiskLruCache.Entry)null);
               this.lruEntries.put(var1, var4);
            } catch (Throwable var36) {
               var10000 = var36;
               var10001 = false;
               break label338;
            }
         } else {
            try {
               var7 = var4.currentEditor;
            } catch (Throwable var35) {
               var10000 = var35;
               var10001 = false;
               break label338;
            }

            if (var7 != null) {
               return null;
            }
         }

         try {
            var7 = new DiskLruCache.Editor(var4, (DiskLruCache.Editor)null);
            var4.currentEditor = var7;
            Writer var40 = this.journalWriter;
            StringBuilder var8 = new StringBuilder("DIRTY ");
            var8.append(var1);
            var8.append('\n');
            var40.write(var8.toString());
            this.journalWriter.flush();
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label338;
         }

         return var7;
      }

      Throwable var39 = var10000;
      try {
         throw var39;
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }
      return null;
   }

   private static String inputStreamToString(InputStream var0) throws IOException {
      return readFully(new InputStreamReader(var0, UTF_8));
   }

   private boolean journalRebuildRequired() {
      return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
   }

   public static DiskLruCache open(File var0, int var1, int var2, long var3) throws IOException {
      if (var3 <= 0L) {
         throw new IllegalArgumentException("maxSize <= 0");
      } else if (var2 <= 0) {
         throw new IllegalArgumentException("valueCount <= 0");
      } else {
         DiskLruCache var5 = new DiskLruCache(var0, var1, var2, var3);
         if (var5.journalFile.exists()) {
            try {
               var5.readJournal();
               var5.processJournal();
               FileWriter var7 = new FileWriter(var5.journalFile, true);
               BufferedWriter var11 = new BufferedWriter(var7);
               var5.journalWriter = var11;
               return var5;
            } catch (IOException var9) {
               PrintStream var6 = System.out;
               StringBuilder var8 = new StringBuilder("DiskLruCache ");
               var8.append(var0);
               var8.append(" is corrupt: ");
               var8.append(var9.getMessage());
               var8.append(", removing");
               var6.println(var8.toString());
               var5.delete();
            }
         }

         var0.mkdirs();
         DiskLruCache var10 = new DiskLruCache(var0, var1, var2, var3);
         var10.rebuildJournal();
         return var10;
      }
   }

   private void processJournal() throws IOException {
      deleteIfExists(this.journalFileTmp);
      Iterator var1 = this.lruEntries.values().iterator();

      while(true) {
         while(var1.hasNext()) {
            DiskLruCache.Entry var2 = (DiskLruCache.Entry)var1.next();
            DiskLruCache.Editor var3 = var2.currentEditor;
            byte var4 = 0;
            int var5 = 0;
            if (var3 == null) {
               while(var5 < this.valueCount) {
                  this.size += var2.lengths[var5];
                  ++var5;
               }
            } else {
               var2.currentEditor = null;

               for(var5 = var4; var5 < this.valueCount; ++var5) {
                  deleteIfExists(var2.getCleanFile(var5));
                  deleteIfExists(var2.getDirtyFile(var5));
               }

               var1.remove();
            }
         }

         return;
      }
   }

   private static String readAsciiLine(InputStream var0) throws IOException {
      StringBuilder var1 = new StringBuilder(80);

      while(true) {
         int var2 = var0.read();
         if (var2 == -1) {
            throw new EOFException();
         }

         if (var2 == 10) {
            var2 = var1.length();
            if (var2 > 0) {
               --var2;
               if (var1.charAt(var2) == '\r') {
                  var1.setLength(var2);
               }
            }

            return var1.toString();
         }

         var1.append((char)var2);
      }
   }

   private static String readFully(Reader var0) throws IOException {
      String var25 = new String();
      label158: {
         Throwable var10000;
         label157: {
            StringWriter var1;
            boolean var10001;
            char[] var2;
            try {
               var1 = new StringWriter();
               var2 = new char[1024];
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label157;
            }

            while(true) {
               int var3;
               try {
                  var3 = var0.read(var2);
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break;
               }

               if (var3 == -1) {
                  try {
                     var25 = var1.toString();
                     break label158;
                  } catch (Throwable var20) {
                     var10000 = var20;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  var1.write(var2, 0, var3);
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var24 = var10000;
         var0.close();
         try {
            throw var24;
         } catch (Throwable throwable) {
            throwable.printStackTrace();
         }
      }

      var0.close();
      return var25;
   }

   private void readJournal() throws IOException {
      final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.journalFile));
      try {
         final String asciiLine = readAsciiLine(bufferedInputStream);
         final String asciiLine2 = readAsciiLine(bufferedInputStream);
         final String asciiLine3 = readAsciiLine(bufferedInputStream);
         final String asciiLine4 = readAsciiLine(bufferedInputStream);
         final String asciiLine5 = readAsciiLine(bufferedInputStream);
         if ("libcore.io.DiskLruCache".equals(asciiLine) && "1".equals(asciiLine2) && Integer.toString(this.appVersion).equals(asciiLine3) && Integer.toString(this.valueCount).equals(asciiLine4)) {
            if ("".equals(asciiLine5)) {
// !LC
//               Label_0113: {
//                  break Label_0113;
//                  try {
//                     while (true) {
//                        this.readJournalLine(readAsciiLine(bufferedInputStream));
//                     }
//                  }
//                  catch (EOFException ex) {
//                     return;
//                  }
//               }
            }
         }
         final StringBuilder sb = new StringBuilder("unexpected journal header: [");
         sb.append(asciiLine);
         sb.append(", ");
         sb.append(asciiLine2);
         sb.append(", ");
         sb.append(asciiLine4);
         sb.append(", ");
         sb.append(asciiLine5);
         sb.append("]");
         throw new IOException(sb.toString());
      }
      finally {
         closeQuietly(bufferedInputStream);
      }
   }


   private void readJournalLine(String var1) throws IOException {
      String[] var2 = var1.split(" ");
      StringBuilder var6;
      if (var2.length < 2) {
         var6 = new StringBuilder("unexpected journal line: ");
         var6.append(var1);
         throw new IOException(var6.toString());
      } else {
         String var4 = var2[1];
         if (var2[0].equals("REMOVE") && var2.length == 2) {
            this.lruEntries.remove(var4);
         } else {
            DiskLruCache.Entry var5 = (DiskLruCache.Entry)this.lruEntries.get(var4);
            DiskLruCache.Entry var3 = var5;
            if (var5 == null) {
               var3 = new DiskLruCache.Entry(var4, (DiskLruCache.Entry)null);
               this.lruEntries.put(var4, var3);
            }

            if (var2[0].equals("CLEAN") && var2.length == this.valueCount + 2) {
               var3.readable = true;
               var3.currentEditor = null;
               var3.setLengths((String[])copyOfRange(var2, 2, var2.length));
            } else if (var2[0].equals("DIRTY") && var2.length == 2) {
               var3.currentEditor = new DiskLruCache.Editor(var3, (DiskLruCache.Editor)null);
            } else if (!var2[0].equals("READ") || var2.length != 2) {
               var6 = new StringBuilder("unexpected journal line: ");
               var6.append(var1);
               throw new IOException(var6.toString());
            }

         }
      }
   }

   private void rebuildJournal() throws IOException {
      synchronized(this){}

      Throwable var10000;
      label313: {
         boolean var10001;
         try {
            if (this.journalWriter != null) {
               this.journalWriter.close();
            }
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label313;
         }

         BufferedWriter var1;
         FileWriter var2;
         Iterator var36;
         try {
            var2 = new FileWriter(this.journalFileTmp);
            var1 = new BufferedWriter(var2);
            var1.write("libcore.io.DiskLruCache");
            var1.write("\n");
            var1.write("1");
            var1.write("\n");
            var1.write(Integer.toString(this.appVersion));
            var1.write("\n");
            var1.write(Integer.toString(this.valueCount));
            var1.write("\n");
            var1.write("\n");
            var36 = this.lruEntries.values().iterator();
         } catch (Throwable var33) {
            var10000 = var33;
            var10001 = false;
            break label313;
         }

         while(true) {
            try {
               if (!var36.hasNext()) {
                  var1.close();
                  this.journalFileTmp.renameTo(this.journalFile);
                  var2 = new FileWriter(this.journalFile, true);
                  var1 = new BufferedWriter(var2);
                  this.journalWriter = var1;
                  return;
               }
            } catch (Throwable var31) {
               var10000 = var31;
               var10001 = false;
               break;
            }

            DiskLruCache.Entry var3;
            StringBuilder var4;
            try {
               var3 = (DiskLruCache.Entry)var36.next();
               if (var3.currentEditor != null) {
                  var4 = new StringBuilder("DIRTY ");
                  var4.append(var3.key);
                  var4.append('\n');
                  var1.write(var4.toString());
                  continue;
               }
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break;
            }

            try {
               var4 = new StringBuilder("CLEAN ");
               var4.append(var3.key);
               var4.append(var3.getLengths());
               var4.append('\n');
               var1.write(var4.toString());
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break;
            }
         }
      }

      Throwable var35 = var10000;
      try {
         throw var35;
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }
   }

   private void trimToSize() throws IOException {
      while(this.size > this.maxSize) {
         this.remove((String)((java.util.Map.Entry)this.lruEntries.entrySet().iterator().next()).getKey());
      }

   }

   private void validateKey(String var1) {
      if (var1.contains(" ") || var1.contains("\n") || var1.contains("\r")) {
         StringBuilder var2 = new StringBuilder("keys must not contain spaces or newlines: \"");
         var2.append(var1);
         var2.append("\"");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void close() throws IOException {
      synchronized(this){}

      Throwable var10000;
      label233: {
         Writer var1;
         boolean var10001;
         try {
            var1 = this.journalWriter;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label233;
         }

         if (var1 == null) {
            return;
         }

         Iterator var2;
         try {
            ArrayList var23 = new ArrayList(this.lruEntries.values());
            var2 = var23.iterator();
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label233;
         }

         while(true) {
            label219: {
               try {
                  if (var2.hasNext()) {
                     break label219;
                  }

                  this.trimToSize();
                  this.journalWriter.close();
                  this.journalWriter = null;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break;
               }

               return;
            }

            try {
               DiskLruCache.Entry var25 = (DiskLruCache.Entry)var2.next();
               if (var25.currentEditor != null) {
                  var25.currentEditor.abort();
               }
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               break;
            }
         }
      }

      Throwable var24 = var10000;
      try {
         throw var24;
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }
   }

   public void delete() throws IOException {
      this.close();
      deleteContents(this.directory);
   }

   public DiskLruCache.Editor edit(String var1) throws IOException {
      return this.edit(var1, -1L);
   }

   public void flush() throws IOException {
      synchronized(this){}

      try {
         this.checkNotClosed();
         this.trimToSize();
         this.journalWriter.flush();
      } finally {
         ;
      }

   }

   public Snapshot get(final String s) throws IOException {
      synchronized (this) {
         this.checkNotClosed();
         this.validateKey(s);
         final Entry entry = (Entry)this.lruEntries.get(s);
         if (entry == null) {
            return null;
         }
         if (!entry.readable) {
            return null;
         }
         final InputStream[] array = new InputStream[this.valueCount];
         int i = 0;
         try {
            while (i < this.valueCount) {
               array[i] = new FileInputStream(entry.getCleanFile(i));
               ++i;
            }
            ++this.redundantOpCount;
            final Writer journalWriter = this.journalWriter;
            final StringBuilder sb = new StringBuilder("READ ");
            sb.append(s);
            sb.append('\n');
            journalWriter.append((CharSequence)sb.toString());
            if (this.journalRebuildRequired()) {
               this.executorService.submit(this.cleanupCallable);
            }
            return new Snapshot(s, entry.sequenceNumber, array, (Snapshot)null);
         }
         catch (FileNotFoundException ex) {
            return null;
         }
      }
   }


   public File getDirectory() {
      return this.directory;
   }

   public boolean isClosed() {
      return this.journalWriter == null;
   }

   public long maxSize() {
      return this.maxSize;
   }

   public boolean remove(String var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label357: {
         DiskLruCache.Entry var2;
         boolean var10001;
         try {
            this.checkNotClosed();
            this.validateKey(var1);
            var2 = (DiskLruCache.Entry)this.lruEntries.get(var1);
         } catch (Throwable var33) {
            var10000 = var33;
            var10001 = false;
            break label357;
         }

         int var3;
         label351: {
            var3 = 0;
            if (var2 != null) {
               try {
                  if (var2.currentEditor == null) {
                     break label351;
                  }
               } catch (Throwable var34) {
                  var10000 = var34;
                  var10001 = false;
                  break label357;
               }
            }

            return false;
         }

         while(true) {
            try {
               if (var3 >= this.valueCount) {
                  ++this.redundantOpCount;
                  Writer var38 = this.journalWriter;
                  StringBuilder var39 = new StringBuilder("REMOVE ");
                  var39.append(var1);
                  var39.append('\n');
                  var38.append(var39.toString());
                  this.lruEntries.remove(var1);
                  if (this.journalRebuildRequired()) {
                     this.executorService.submit(this.cleanupCallable);
                  }
                  break;
               }
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break label357;
            }

            try {
               File var4 = var2.getCleanFile(var3);
               if (!var4.delete()) {
                  StringBuilder var36 = new StringBuilder("failed to delete ");
                  var36.append(var4);
                  IOException var37 = new IOException(var36.toString());
                  throw var37;
               }
            } catch (Throwable var31) {
               var10000 = var31;
               var10001 = false;
               break label357;
            }

            try {
               this.size -= var2.lengths[var3];
               var2.lengths[var3] = 0L;
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label357;
            }

            ++var3;
         }

         return true;
      }

      Throwable var35 = var10000;
      try {
         throw var35;
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }
      return false;
   }

   public long size() {
      synchronized(this){}

      long var1;
      try {
         var1 = this.size;
      } finally {
         ;
      }

      return var1;
   }

   public final class Editor {
      private final DiskLruCache.Entry entry;
      private boolean hasErrors;

      private Editor(DiskLruCache.Entry var2) {
         this.entry = var2;
      }

      // $FF: synthetic method
      Editor(DiskLruCache.Entry var2, DiskLruCache.Editor var3) {
         this(var2);
      }

      public void abort() throws IOException {
         DiskLruCache.this.completeEdit(this, false);
      }

      public void commit() throws IOException {
         if (this.hasErrors) {
            DiskLruCache.this.completeEdit(this, false);
            DiskLruCache.this.remove(this.entry.key);
         } else {
            DiskLruCache.this.completeEdit(this, true);
         }

      }

      public String getString(int var1) throws IOException {
         InputStream var2 = this.newInputStream(var1);
         String var3;
         if (var2 != null) {
            var3 = DiskLruCache.inputStreamToString(var2);
         } else {
            var3 = null;
         }

         return var3;
      }

      public InputStream newInputStream(int var1) throws IOException {
         DiskLruCache var2 = DiskLruCache.this;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label197: {
            try {
               if (this.entry.currentEditor != this) {
                  IllegalStateException var25 = new IllegalStateException();
                  throw var25;
               }
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label197;
            }

            try {
               if (!this.entry.readable) {
                  return null;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label197;
            }

            label184:
            try {
               FileInputStream var24 = new FileInputStream(this.entry.getCleanFile(var1));
               return var24;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label184;
            }
         }

         while(true) {
            Throwable var3 = var10000;

            try {
               throw var3;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               continue;
            }
         }
      }

      public OutputStream newOutputStream(int var1) throws IOException {
         DiskLruCache var2 = DiskLruCache.this;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label122: {
            try {
               if (this.entry.currentEditor != this) {
                  IllegalStateException var18 = new IllegalStateException();
                  throw var18;
               }
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label122;
            }

            label116:
            try {
               FileOutputStream var17 = new FileOutputStream(this.entry.getDirtyFile(var1));
               DiskLruCache.Editor.FaultHidingOutputStream var4 = new DiskLruCache.Editor.FaultHidingOutputStream(var17, (DiskLruCache.Editor.FaultHidingOutputStream)null);
               return var4;
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label116;
            }
         }

         while(true) {
            Throwable var3 = var10000;

            try {
               throw var3;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               continue;
            }
         }
      }

      public void set(int var1, String var2) throws IOException {
         Object var3 = null;
         boolean var9 = false;

         OutputStreamWriter var4;
         try {
            var9 = true;
            var4 = new OutputStreamWriter(this.newOutputStream(var1), DiskLruCache.UTF_8);
            var9 = false;
         } finally {
            if (var9) {
               DiskLruCache.closeQuietly((Closeable)var3);
            }
         }

         try {
            var4.write(var2);
         } finally {
            ;
         }

         DiskLruCache.closeQuietly(var4);
      }

      private class FaultHidingOutputStream extends FilterOutputStream {
         private FaultHidingOutputStream(OutputStream var2) {
            super(var2);
         }

         // $FF: synthetic method
         FaultHidingOutputStream(OutputStream var2, DiskLruCache.Editor.FaultHidingOutputStream var3) {
            this(var2);
         }

         public void close() {
            try {
               this.out.close();
            } catch (IOException var2) {
               Editor.this.hasErrors = true;
            }

         }

         public void flush() {
            try {
               this.out.flush();
            } catch (IOException var2) {
               Editor.this.hasErrors = true;
            }

         }

         public void write(int var1) {
            try {
               this.out.write(var1);
            } catch (IOException var3) {
               Editor.this.hasErrors = true;
            }

         }

         public void write(byte[] var1, int var2, int var3) {
            try {
               this.out.write(var1, var2, var3);
            } catch (IOException var4) {
               Editor.this.hasErrors = true;
            }

         }
      }
   }

   private final class Entry {
      private DiskLruCache.Editor currentEditor;
      private final String key;
      private final long[] lengths;
      private boolean readable;
      private long sequenceNumber;

      private Entry(String var2) {
         this.key = var2;
         this.lengths = new long[DiskLruCache.this.valueCount];
      }

      // $FF: synthetic method
      Entry(String var2, DiskLruCache.Entry var3) {
         this(var2);
      }

      private IOException invalidLengths(String[] var1) throws IOException {
         StringBuilder var2 = new StringBuilder("unexpected journal line: ");
         var2.append(Arrays.toString(var1));
         throw new IOException(var2.toString());
      }

      private void setLengths(String[] var1) throws IOException {
         if (var1.length != DiskLruCache.this.valueCount) {
            throw this.invalidLengths(var1);
         } else {
            int var2 = 0;

            while(true) {
               try {
                  if (var2 >= var1.length) {
                     return;
                  }

                  this.lengths[var2] = Long.parseLong(var1[var2]);
               } catch (NumberFormatException var4) {
                  throw this.invalidLengths(var1);
               }

               ++var2;
            }
         }
      }

      public File getCleanFile(int var1) {
         File var2 = DiskLruCache.this.directory;
         StringBuilder var3 = new StringBuilder(String.valueOf(this.key));
         var3.append(".");
         var3.append(var1);
         return new File(var2, var3.toString());
      }

      public File getDirtyFile(int var1) {
         File var2 = DiskLruCache.this.directory;
         StringBuilder var3 = new StringBuilder(String.valueOf(this.key));
         var3.append(".");
         var3.append(var1);
         var3.append(".tmp");
         return new File(var2, var3.toString());
      }

      public String getLengths() throws IOException {
         StringBuilder var1 = new StringBuilder();
         long[] var2 = this.lengths;
         int var3 = 0;

         for(int var4 = var2.length; var3 < var4; ++var3) {
            long var5 = var2[var3];
            var1.append(' ');
            var1.append(var5);
         }

         return var1.toString();
      }
   }

   public final class Snapshot implements Closeable {
      private final InputStream[] ins;
      private final String key;
      private final long sequenceNumber;

      private Snapshot(String var2, long var3, InputStream[] var5) {
         this.key = var2;
         this.sequenceNumber = var3;
         this.ins = var5;
      }

      // $FF: synthetic method
      Snapshot(String var2, long var3, InputStream[] var5, DiskLruCache.Snapshot var6) {
         this(var2, var3, var5);
      }

      public void close() {
         InputStream[] var1 = this.ins;
         int var2 = 0;

         for(int var3 = var1.length; var2 < var3; ++var2) {
            DiskLruCache.closeQuietly(var1[var2]);
         }

      }

      public DiskLruCache.Editor edit() throws IOException {
         return DiskLruCache.this.edit(this.key, this.sequenceNumber);
      }

      public InputStream getInputStream(int var1) {
         return this.ins[var1];
      }

      public String getString(int var1) throws IOException {
         return DiskLruCache.inputStreamToString(this.getInputStream(var1));
      }
   }
}

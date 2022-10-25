 package utils;

 public class CustomHelpers {

   public static boolean isJson(String str) {
     if (str == null || str.isEmpty())
       return false; 
     String str2 = str.trim();
     if (str2.startsWith("{") && str2.endsWith("}"))
       return true; 
     if (!str2.startsWith("[") || !str2.endsWith("]"))
       return false; 
     return true;
   }
 }



package com.ruizton.main.service.comm.redis;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.mysql.jdbc.SQLError;
import com.ruizton.util.Utils;



public class MethodCacheInterceptor implements MethodInterceptor {
    private Logger logger = Logger.getLogger(MethodCacheInterceptor. class);
    private RedisUtil redisUtil;
    private List<String> targetNamesList; // 不加入缓存的service名称
    private List<String> methodNamesList; // 不加入缓存的方法名称
    private Long defaultCacheExpireTime; //缓存默认的过期时间
    private Long xxxRecordManagerTime; //
    private Long xxxSetRecordManagerTime; //
    /**
     * 初始化读取不需要加入缓存的类名和方法名称
     */
    public MethodCacheInterceptor() {
          try {
             InputStream in = getClass().getClassLoader().getResourceAsStream("cacheConf.properties" );
             Properties p = new Properties();
             p.load(in);
              // 分割字符串
             String[] targetNames = p.getProperty("targetNames" ).split(",");
             String[] methodNames = p.getProperty("methodNames" ).split(",");
             
              //加载过期时间设置
              defaultCacheExpireTime = Long.valueOf(p.getProperty("defaultCacheExpireTime"));
              xxxRecordManagerTime = Long.valueOf(p.getProperty("com.service.impl.xxxRecordManager"));
              xxxSetRecordManagerTime = Long.valueOf(p.getProperty("com.service.impl.xxxSetRecordManager"));
              // 创建list
              targetNamesList = new ArrayList<String>(targetNames.length );
              methodNamesList = new ArrayList<String>(methodNames.length );
             Integer maxLen = targetNames. length > methodNames.length ? targetNames.length : methodNames.length;
              // 将不需要缓存的类名和方法名添加到list中
              for(int i = 0; i < maxLen; i++) {
                  if(i < targetNames.length ) {
                      targetNamesList.add(targetNames[i]);
                 }
                  if(i < methodNames.length ) {
                      methodNamesList.add(methodNames[i]);
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    
    
    public Object invoke(MethodInvocation invocation) throws Throwable {
         Object value = null;

         String targetName = invocation.getThis().getClass().getName();
         String methodName = invocation.getMethod().getName();
          // 不需要缓存的内容
          if (!isAddCache(Utils.subStrForLastDot(targetName), methodName)) {
              // 执行方法返回结果
              return invocation.proceed();
         }
         Object[] arguments = invocation.getArguments();
         String key = getCacheKey(targetName, methodName, arguments);

          try {
              // 判断是否有缓存
              if (redisUtil .exists(key)) {
//            	  System. out.println("Redis:"+methodName);
                  return redisUtil .get(key);
             }
              // 写入缓存
             value = invocation.proceed();
              if (value != null) {
                  final String tkey = key;
                  final Object tvalue = value;
                  new Thread(new Runnable() {
                       public void run() {
                           if(tkey.startsWith("com.service.impl.xxxRecordManager" )){
                                redisUtil.set(tkey, tvalue,xxxRecordManagerTime );
                          } else if(tkey.startsWith("com.service.impl.xxxSetRecordManager" )){
                                redisUtil.set(tkey, tvalue,xxxSetRecordManagerTime );
                          } else{
                                redisUtil.set(tkey, tvalue,defaultCacheExpireTime );
                          }
                      }
                 }).start();
             }
         } catch (Exception e) {
             e.printStackTrace();
              if (value == null) {
                  return invocation.proceed();
             }
         }
          return value;
    }
    
    /**
     * 是否加入缓存
     * @return
     */
    private boolean isAddCache(String targetName, String methodName) {
          boolean flag = true;
          if(targetNamesList .contains(targetName) || methodNamesList.contains(methodName)) {
             flag = false;
         }
          
         return flag ;
    }

    /**
     * 创建缓存key
     *
     * @param targetName
     * @param methodName
     * @param arguments
     */
    private String getCacheKey(String targetName, String methodName,
             Object[] arguments) {
         StringBuffer sbu = new StringBuffer();
        sbu.append(RedisConstant.cacheHeader+targetName).append( "_").append(methodName);
          if ((arguments != null) && (arguments.length != 0)) {
              for (int i = 0; i < arguments.length; i++) {
            	  Object obj = arguments[i] ;
            	  String arg = null; 
            	  if(obj.getClass().isInstance(Class.class)){
            		  Class clazz = (Class)obj ;
            		  arg = clazz.getName() ;
            	  }else if(obj.getClass().isAssignableFrom(Object[].class)){
            		  Object args[] = (Object[])obj ;
            		  StringBuffer sb = new StringBuffer() ;
            		  for (int j = 0; j < args.length; j++) {
            			  sb.append("_"+String.valueOf(args[j])) ;
            		  }
            		  arg = sb.toString() ;
            	  }else{
            		  arg = String.valueOf(obj) ;
            	  }
                 sbu.append( "_").append(arg);
             }
         }
          return sbu.toString();
    }

    public void setRedisUtil(RedisUtil redisUtil) {
          this.redisUtil = redisUtil;
    }
}
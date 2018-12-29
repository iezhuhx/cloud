package com.cyb.utils.charset;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月12日
 */
public interface EntryCoder {
   String defaultUTF8="UTF-8";
   String defaultGbk="GBK";
   String encode(String name);
   String decode(String name);
}

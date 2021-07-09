package com.ebsite.tempsite.ebsecurity.core.urlmanager;

import org.springframework.util.AntPathMatcher;

/**
 * 基于 AntPathMatcher的单例
 *
 * @author 蔡齐盛
 * @create 2017-12-07 11:43
 **/

public class UrlMatcher {
      private static class SingletonHolder
      {
            private static final AntPathMatcher INSTANCE = new AntPathMatcher();
      }
      private UrlMatcher (){}
      public static final AntPathMatcher getInstance() {
                 return SingletonHolder.INSTANCE;
             }
  }

package com.ebsite.tempsite.ebsecurity.core.valcode;


import com.ebsite.tempsite.ebsecurity.core.configs.LoginConstants;

public enum EmValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return LoginConstants.Default_Parameter_Name_Code_Sms;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return LoginConstants.Default_Parameter_Name_Code_Sms_Image;
        }

    },
    EMAIL {
        @Override
        public String getParamNameOnValidate() {
            return LoginConstants.Default_Parameter_Name_Code_Email;
        }
    }
    ,
    JSRANDOM {
        @Override
        public String getParamNameOnValidate() {
            return LoginConstants.Default_Parameter_Name_Code_JsRandom;
        }
    }
    ,
    GOOGLE {
        @Override
        public String getParamNameOnValidate() {
            return LoginConstants.Default_Parameter_Name_Code_Google;
        }
    }
    ;

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    public abstract String getParamNameOnValidate();
}

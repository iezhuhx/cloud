package com.sihai.utils;

public class AlipayConfig {

	public static String app_id = "2016100100637464";//�ں�̨��ȡ���������ã�
	
	public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDt4+1bwDOfAboyloj1FPaTw1bveGTvgTQFORVbL+CcZrCfF6dBRQKdl90VuAKViIOsbjPUrDzZ4t5p09/eZi3y00+efrdPAvIzN6HfJhCadq18tDpGZwOvfJj2LDn7l1cm9oC8SIVcbh5kD8dM70i4FBYTxvPwg6bCOQLVlKtBO6agiV85VOiM2CfqIUugQMPo3So4TrUYOQkRBb5puxsDtIwhZZY2yTH33Uzuu5Ird6rsvejF0h62dI4pmjdd4AdHqVlljW+P9pMmguBAx2jecc5LwXWdM6/dzegPgmzwrdDIo5OVtowqRqY4DShO1mXVXRaXtybdwyhXLonxP8cPAgMBAAECggEBANxm0y+xmGIWaJucCtH+lSF71tzRj7qy2y6Hn7i3WAOvxK3xbT20oWK/I8+Oh55hN5WXGKBO36YDYotsCLr1slaK3vZ6cn1avmkjfu7PRozFraCfHmrB4JFDADj76tCle9KWidln218yiyer0rv96kLaIUY7iyDn6KWDypN1RWGOCDSocrjZ3GPWIWK9BFvmlDjZW/ux4f9IIMxPQnD0PtYlAAtHPxXIk5BOjBNIaZAVouvA0amUphZR3yGqlEWWEI6bMQ7iNbadg7Hdhqb9a/hkQW+SsIU8j+In9CdvAVBCjX45jV+htZCufSF5G9pN6RcSaU0FqvxqO6ED55pKaJECgYEA/ZuiQ35wGsfD3jfdJu+AMQng9EdtQlsiifkEEEAP61gzYrWhhJr5apuJcSbkZEi/iSgMDQlNOHtMlFL8XHvX/ci506A28yQ9qRk7nOgqHLlaNFDlyYgaSPg5VFPrVOU9W4DavQRvshUNg1xDlD3TNWLzZEbc01I9vSBkX8z8OykCgYEA8CJXYfLbPID/vd5XDWRy4MvULMdurGXdIIcuiof/bx8KSoMlu/Ui4sqXhqhMKkurvq7gl7C86HHW9w2tLCpOrHsNunjqNoN/BnrPAg+rrt7ygAnowSaas73NP8mop7z4EB+Ylx4zdPCwqRARC3uVvIuTsSVo6pEgsejFnfQ973cCgYEA9fwSPGMUO3WN7ynCUTS241nQnz/0owsgijFFlrl8d2qalODVpHho+dWZT25I01iYp2F3puFhzCoUEj4X12CsFNWwIKtjAR0izbTS1JN/3VKPQvVAnORtkl9pw/iN509e3zUxdWsrJ9ExxTsltVDuXvb2W0o8gKBhj+MuWJnMAVkCgYAGksoVklJSC630gpubcD58zNeYCjWLXpZ5B3pMrIBfiFf9xi1IQeOclbMkKoM7PSsMYzkereXd7w/VX3h4FwhkTFIJ4rl2mI7nnIECK2+lr9CvlJXcg7QEa5OsxVZqkQ+xUUwaBuedseEEH753tABbUu7OEJoYobRlalvOgRsY7QKBgE01TLu/oxuNN8xUxfv0NRwrqtRR/ghOQrwVwPhhxXMUpMrXpDx2JgHB354NL1n0MTq/Lr+f+dhs86L6zS8EIViFWTl7mChxXhlcrvnj9SuzuXLB7P4DPQZECCJNM/3Iae1TZm124aQ7fmAUbLqgBzeetUS5+P5BiqwxHsSa0GBM";//�̳̲鿴��ȡ��ʽ���������ã�

	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3CtIknN7icgf8UvNITSuRKrCWoHIDNr5i7zufwZqRuglkEh9sCnwCscZPPlzsF/NLQTXjnj/9wdfd+l5tDVHSCH0X5e2sZNDDkwr7XIgPpjZncPVmvC8UxfIqTHIN2QOQhGJq21ftgbryShXYvkYEtgy5BUu+5R280y98izU6VtFnqsbR+RSHfdSPXWDLfu7ynB5aih+vdrXdCTecg97jFnhuw39wnBrsut53jCKp4f3oMkWV910Mq226SeRTvVX2mIUmcorud9e/HpeIGh2cynEJfZ5DkIUKsdHu8rmLlcruXm3+5LV1mdk0vAM0zukOCmqO7ByKDGYG/lAE7hjAQIDAQAB";//�̳̲鿴��ȡ��ʽ���������ã�
	
	public static String notify_url = "http://localhost:8088/itzixi-maven-ssm-alipay/alipay/alipayNotifyNotice.action";
	
	public static String return_url = "http://localhost:8088/itzixi-maven-ssm-alipay/alipay/alipayReturnNotice.action";
	
	public static String sign_type = "RSA2";
	
	public static String charset = "utf-8";
	
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";//ע�⣺ɳ����Ի�������ʽ����Ϊ��https://openapi.alipay.com/gateway.do
}

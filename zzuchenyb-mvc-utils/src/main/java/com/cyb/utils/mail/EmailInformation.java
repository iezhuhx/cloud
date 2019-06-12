package com.cyb.utils.mail;

import lombok.Data;

/**
 *作者 : iechenyb<br>
 *类描述: 建造者模式<br>
 *创建时间: 2019年1月15日
 */
@Data
public class EmailInformation {
	public static void main(String[] args) {
		new EmailInformation
		.Builder()
		.from("")
		.to("")
		.build();
	}
    public String from;
    public String to;
    public String subject;
    public String emailContent;
    public String date;



    public static class Builder {
        private String from;
        private String to;
        private String subject;
        private String emailContent;
        private String date;

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }
        public Builder emailContent(String emailContent) {
            this.emailContent = emailContent;
            return this;
        }
        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public EmailInformation build() {
            return new EmailInformation(this);
        }
    }

    public EmailInformation(Builder builder) {
        from = builder.from;
        to = builder.to;
        subject = builder.subject;
        emailContent = builder.emailContent;
        date = date;
    }
}

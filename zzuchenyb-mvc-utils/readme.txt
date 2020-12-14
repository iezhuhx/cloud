1 因为install时，打了一个mvc的jar包，导致无法生成所有的jar文件。
所以，需要手动的生成jar文件集合。步骤如下：
1 点击pom.xml邮件run as
2 Goals框中输入“dependency:copy-dependencies”，后点击“Run”即可。
3 生成的目标目录为 zzuchenyb-mvc-utils\target\dependency
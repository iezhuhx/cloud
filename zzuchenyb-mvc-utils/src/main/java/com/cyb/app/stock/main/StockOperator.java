package com.cyb.app.stock.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.app.h2.usecase.H2Starter;
import com.cyb.app.stock.pub.RealQuotation;
import com.cyb.app.stock.pub.Stock;
import com.cyb.app.stock.util.BanBanCode;
import com.cyb.app.stock.util.DrawQutoesUtils;
import com.cyb.app.stock.util.StrateStockDbUtils;
import com.cyb.utils.page.Pagination;

/**
 * @Author iechenyb<br>
 * @Desc 创业板申购代码、上市代码都是30****，增发37****，配股38****<br>
 *       A股：沪市 600***或者60****,深市000***或001*** 中小板00**** B股：沪市900×××，深市200×××
 *       后三位大小可以表示上市先后顺序。
 * @CreateTime 2020年12月9日 下午3:36:24
 */
public class StockOperator {
	Log log = LogFactory.getLog(StockOperator.class);
	static boolean initStockCode = true;

	public static void main(String[] args) throws IOException, SQLException {
		H2Starter.startTcpAndWebServer();
		StrateStockDbUtils.initStockDB(false);// 初始化表
		// StrateStockDbUtils.initStockInfo2H2("sh");//初始化上海证券交易所的股票代码
		// StrateStockDbUtils.initStockInfo2H2("sz");//初始化深证证券交易所的股票代码
		List<Stock> stocks = BanBanCode.analyseCodeFromHtml();
		if (initStockCode) {// 落库
			StrateStockDbUtils.saveStockCode(stocks);// 2089只2020年12月9日16:46:28
		}
		boolean getRealQutoes = false;
		if(!getRealQutoes) return ;
		/**
		 * 抓取行情时，容易被禁用。
		 */
		Pagination page = new Pagination(1, 50, stocks.size());
		for (int i = 1; i <= page.getPageCount(); i++) {
			Pagination cur = page.getPage(i);
			StringBuffer codes = new StringBuffer();
			for (int j = cur.getPageStart(); j <= cur.getPageEnd() + 1; j++) {
				if (j > stocks.size())
					continue;
				if (j != cur.getPageEnd() + 1) {
					codes.append(stocks.get(j).getCode() + ",");
				} else {
					codes.append(stocks.get(j - 1).getCode());
				}
			}
			List<RealQuotation> qutoes = DrawQutoesUtils.getRealQutoesBatch(codes.toString());// 抓取行情
			StrateStockDbUtils.saveRealQutoes(qutoes);// 行情落库
		}
	}
}

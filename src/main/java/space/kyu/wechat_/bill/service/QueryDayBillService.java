package space.kyu.wechat_.bill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import space.kyu.wechat_.bill.dao.BaseDao;
import space.kyu.wechat_.bill.entity.AccountDetail;
import space.kyu.wechat_.common.service.AbstractTextMsgService;

/**
 * 查询日账单
 * 
 * @author yukai
 *
 */
@Service
public class QueryDayBillService extends AbstractTextMsgService {

	@Autowired
	private BaseDao dao;

	@Override
	protected String dealwithRequest(String openid, String content) {
		String date = content;
		List<AccountDetail> incomes = dao.getIncomeDetailsByDay(openid, date);
		List<AccountDetail> outlays = dao.getOutLayDetailsByDay(openid, date);
		AccountDetail detail = null;
		int incomeNum = 0;
		int outlayNum = 0;
		StringBuilder resp = new StringBuilder();
		char lineFeed = '\n';
		resp.append(date)
		    .append("收支明细:").append(lineFeed);
		resp.append("收入:").append(lineFeed);
		if (incomes != null && incomes.size() > 0) {
			for(int i = 0; i < incomes.size(); i++) {
				detail = incomes.get(i);
				resp.append(detail.getDescription())
					.append(":").append(detail.getNum()).append(lineFeed);
				incomeNum += Integer.valueOf(detail.getNum()); 
			}
		}
		resp.append("总收入:").append(incomeNum).append(lineFeed);
		resp.append("支出:").append(lineFeed);
		if (outlays != null && outlays.size() > 0) {
			for(int i = 0; i < outlays.size(); i++) {
				detail = outlays.get(i);
				resp.append(detail.getDescription())
					.append(":").append(detail.getNum()).append(lineFeed);
				outlayNum += Integer.valueOf(detail.getNum());
			}
		}
	    resp.append("总支出:").append(outlayNum).append(lineFeed);	
		return resp.toString();
	}

}

package com.bestway.common.erpbill.entity;

import java.io.Serializable;
import java.util.List;

public class TempDateCheck implements Serializable {

		public List listCustomOrderDetail;
		public List listError;
		public List getListCustomOrderDetail() {
			return listCustomOrderDetail;
		}
		public void setListCustomOrderDetail(List listCustomOrderDetail) {
			this.listCustomOrderDetail = listCustomOrderDetail;
		}
		public List getListError() {
			return listError;
		}
		public void setListError(List listError) {
			this.listError = listError;
		}

}

package com.hrm.dto.emp;

import java.io.Serializable;

/**
 * @Description:
 */
public class employeePercentBySex extends objectTotal implements Serializable {
        private Integer male;

        private Integer famale;

        private Integer total;

        public Integer getMale() {
                return male;
        }

        public void setMale(Integer male) {
                this.male = male;
        }

        public Integer getFamale() {
                return famale;
        }

        public void setFamale(Integer famale) {
                this.famale = famale;
        }

        @Override
        public Integer getTotal() {
                return total;
        }

        @Override
        public void setTotal(Integer total) {
                this.total = total;
        }
}

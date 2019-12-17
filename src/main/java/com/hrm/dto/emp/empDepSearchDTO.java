package com.hrm.dto.emp;

import com.hrm.bean.Employee;

import java.io.Serializable;

/**
 * @Description:
 */
public class empDepSearchDTO extends Employee implements Serializable {

        private String depname;

        private String depleader;

        private String hiredateStr;

        public String getDepname() {
                return depname;
        }

        public void setDepname(String depname) {
                this.depname = depname;
        }

        public String getDepleader() {
                return depleader;
        }

        public void setDepleader(String depleader) {
                this.depleader = depleader;
        }

        public String getHiredateStr() {
                return hiredateStr;
        }

        public void setHiredateStr(String hiredateStr) {
                this.hiredateStr = hiredateStr;
        }

        @Override
        public String toString() {
                return "empDepSearchDTO{" +
                        "depname='" + depname + '\'' +
                        ", depleader='" + depleader + '\'' +
                        ", hiredateStr='" + hiredateStr + '\'' +
                        '}';
        }
}

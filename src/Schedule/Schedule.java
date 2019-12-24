package Schedule;

import javafx.beans.property.SimpleStringProperty;

public class Schedule {
    private SimpleStringProperty branch;
    private  SimpleStringProperty day;
    private  SimpleStringProperty lecture1;
    private  SimpleStringProperty lecture2;
    private  SimpleStringProperty lecture3;
    private  SimpleStringProperty lecture4;
    private  SimpleStringProperty lecture5;
    private  SimpleStringProperty lecture6;

    public Schedule(String branch, String day, String lecture1, String lecture2, String lecture3, String lecture4, String lecture5, String lecture6) {
        this.branch= new SimpleStringProperty(branch);
        this.day = new SimpleStringProperty(day);
        this.lecture1 = new SimpleStringProperty(lecture1);
        this.lecture2 = new SimpleStringProperty(lecture2);
        this.lecture3 = new SimpleStringProperty(lecture3);
        this.lecture4 = new SimpleStringProperty(lecture4);
        this.lecture5 = new SimpleStringProperty(lecture5);
        this.lecture6 = new SimpleStringProperty(lecture6);



    }


    public String getBranch() {
        return branch.get();
    }



    public String getDay() {
        return day.get();
    }


    public String getLecture1() {
        return lecture1.get();
    }


    public String getLecture2() {
        return lecture2.get();
    }


    public String getLecture3() {
        return lecture3.get();
    }



    public String getLecture4() {
        return lecture4.get();
    }



    public String getLecture5() {
        return lecture5.get();
    }


    public String getLecture6() {
        return lecture6.get();
    }


}

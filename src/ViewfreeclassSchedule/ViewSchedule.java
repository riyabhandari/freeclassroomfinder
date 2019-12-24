package ViewfreeclassSchedule;

import javafx.beans.property.SimpleStringProperty;

public class ViewSchedule {
    private  SimpleStringProperty lecture;
    private  SimpleStringProperty mon;
    private  SimpleStringProperty tue;
    private  SimpleStringProperty wed;
    private  SimpleStringProperty thu;
    private  SimpleStringProperty fri;

    public ViewSchedule( String lecture, String mon,String tue,String wed,String thu,String fri) {
        this.lecture= new SimpleStringProperty(lecture);
        this.mon = new SimpleStringProperty(mon);
        this.tue = new SimpleStringProperty(tue);
        this.wed = new SimpleStringProperty(wed);
        this.thu = new SimpleStringProperty(thu);
        this.fri = new SimpleStringProperty(fri);



    }




    public String getLecture() {
        return lecture.get();
    }



    public String getMon() {
        return mon.get();
    }



    public String getTue() {
        return tue.get();
    }



    public String getWed() {
        return wed.get();
    }



    public String getThu() {
        return thu.get();
    }



    public String getFri() {
        return fri.get();
    }


}

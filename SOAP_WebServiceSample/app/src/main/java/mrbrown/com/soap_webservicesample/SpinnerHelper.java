package mrbrown.com.soap_webservicesample;

/**
 * Created by mrbrown on 3/9/2017.
 */

public class SpinnerHelper {

    private String Id;
    private String Name;

    public SpinnerHelper(String natureProblemId, String natureProblemname) {
        this.Id = natureProblemId;
        this.Name = natureProblemname;

        setName(Name);
        setId(Id);

    }

    public String getId() {
        return Id;
    }

    public void setId(String natureProblemId) {
        Id = natureProblemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String natureProblemname) {
        Name = natureProblemname;
    }

    //to display object as a string in spinner
    @Override
    public String toString() {
        return Name;
    }

}

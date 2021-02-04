package taskone;

import java.util.List;
import java.util.ArrayList;

class StringList {
    
    List<String> strings = new ArrayList<String>();

    public void add(String str) {
        int pos = strings.indexOf(str);
        if (pos < 0) {
            strings.add(str);
        }
    }

    public boolean contains(String str) {
        return strings.indexOf(str) >= 0;
    }

    public int size() {
        return strings.size();
    }

    public String toString() {
        return strings.toString();
    }

    public String pop () {
        if (this.size() == 0)
            return "null";

        return strings.get(this.size()-1);
    }

    public String count () {
        return Integer.toString(this.size());
    }

    public String display () {
        String temp = "";
        for (String s : strings)
            temp = temp + s + "\n";
        return temp;
    }

    public boolean switch_elements (String in) {
        String temp[] = in.split(" ");
        int indexOne = Integer.parseInt(temp[0]);
        int indexTwo = Integer.parseInt(temp[1]);
        if (indexOne < 0 || indexOne >= this.size())
            return false;
        if (indexTwo < 0 || indexTwo >= this.size())
            return false;
        String tempString = strings.get(indexOne);
        strings.set(indexOne, strings.get(indexTwo));
        strings.set(indexTwo, tempString);
        return true;
    }

}
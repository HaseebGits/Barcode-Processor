public class Refrigeration implements Module{
    private boolean refrigerated;
    private boolean valid;
    public Refrigeration(String flag){             //If i use flag == "1" it may fail because even if two strings have the same characters, they may be different objects in memory.
        if(flag.equals("0")||flag.equals("1")){ //so I use equals instead of == because to check string with validity (comparing) this is correct if i use == it may fail
            refrigerated=flag.equals("1");// here check validation if flag is 1 it should be refrigerated if flag is 0 it should not be refrigerated
            valid=true;
        }else{
            valid = false;
        }
    }
    @Override
    public boolean isValid(){
        return valid;
    }
    public boolean needsRefrigeration(){
        return refrigerated;
    }
}

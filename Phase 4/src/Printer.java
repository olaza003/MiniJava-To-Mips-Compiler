import cs132.vapor.ast.VDataSegment;

public class Printer {
    public String dataSeg = "";
    private static final String IDENT = "  ";
    private String ident = "";
    //handles the extra like .data, class with its methods or dataSegments
    public String printDataSeg(VDataSegment[] cov){
        dataSeg += ".data\n\n";
        increaseIdent();
        for(int i = 0; i < cov.length; i++){
            dataSeg += cov[i].ident + ":\n";
            for(int k = 0; k < cov[i].values.length; k++){
                dataSeg += ident + cov[i].values[k].toString().substring(1) + "\n";
            }
            dataSeg += "\n";
        }
        decreaseIdent();
        dataSeg += "\n" + ident + ".text\n\n";
        increaseIdent();
        dataSeg += ident + "jal Main\n";
        dataSeg += ident + "li $v0 10\n";
        dataSeg += ident + "syscall";
        decreaseIdent();
        return dataSeg;
    }

    public void increaseIdent(){ident += IDENT;}
    public void decreaseIdent(){ident = ident.substring(0, ident.length() - IDENT.length());}
}

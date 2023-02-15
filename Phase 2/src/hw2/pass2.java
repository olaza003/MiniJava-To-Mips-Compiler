package hw2;
import visitor.*;
import syntaxtree.*;
import java.util.*;

import hw2.*;
import hw2.Types.Scope;
import hw2.prints.*;

import java.io.*;



public class pass2 extends GJDepthFirst<Scope, String> {
    holders hold;
    String ret_output = "";
    String currClass = "";
    String paramList = "this";
    String exprList = "";
    Boolean isAllocArray = false;
    Output outp = new Output();
    CodeGenHelper gen = new CodeGenHelper();
    VariableGen varGen = new VariableGen();
    int ssCount = 1;

    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public pass2(Goal goal, holders h){
        
        //-System.out.println("test 1");
        //System.out.println("Pass2 Goal enter");
        hold = h; 
        printer p = new printer(hold);
        String out = p.print();
        //System.out.println(out);
        //System.out.println("test");
        goal.f0.accept(this, null);
        goal.f1.accept(this, null);
        if(isAllocArray)
            allocArrayPrinter();
        //int i = hold.getOffset("Visitor");
        String ret = outp.getOutput();
        ret_output += ret;
        //System.out.println("Pass2 Ends " ); //+ i
        //System.out.println(ret);
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
    public Scope visit(MainClass n, String param){
        //System.out.println("Maincl");
        outp.addLine();
        outp.addtext("func Main()");
        outp.increaseIdent();
        n.f15.accept(this, null); //statement
        outp.addtext("ret");
        outp.decreaseIdent();
        //outp.addLine();
        varGen.resetCount();

        return null;

    }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public Scope visit(TypeDeclaration n, String param){
        //System.out.println("Typedec");
        
        Scope str = n.f0.accept(this, null);

        //System.out.println("Exit Typedec");
        return null;
    }


    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public Scope visit(ClassDeclaration n, String param){
        //System.out.println("Classdec");
        currClass = n.f1.f0.tokenImage;
        n.f3.accept(this, null); //vardec
        n.f4.accept(this, null); //methoddecl
        return null;
    }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
    public Scope visit(ClassExtendsDeclaration n, String param){
        //System.out.println("ClassExtendDec");
        currClass = n.f1.f0.tokenImage;
        n.f5.accept(this, null); //vardecl
        n.f6.accept(this, null); //methoddecl
        return null;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public Scope visit(VarDeclaration n, String param){
        //System.out.println("VarDec");
        return null;
    }

    /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
    public Scope visit(MethodDeclaration n, String param){
        //System.out.println("\n\nMethodDec: " + n.f2.f0.tokenImage);
        paramList = "this";
        exprList = "";
        varGen.resetCount();
        String ident = n.f2.f0.tokenImage; //identifier

        n.f4.accept(this, null); //formalparameterlist
        String funcPrint = "\nfunc " + currClass + "." + ident + "(" + paramList + ")";

        outp.addtext(funcPrint);
        outp.increaseIdent();
        
        n.f7.accept(this, null); //vardeclaration
        n.f8.accept(this, null); //statement
        Scope ret_str = n.f10.accept(this, null); //ret expression
        outp.addtext("ret " + ret_str.getName());
        outp.decreaseIdent();
        return null;
    }

    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    public Scope visit(FormalParameterList n, String param){
        //System.out.println("FormalParamList");
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        return null;
    }

    /**
    * Grammar production:
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public Scope visit(FormalParameter n, String param){
        //System.out.println("FormalParam: " + n.f1.f0.tokenImage);
        paramList += " " + n.f1.f0.tokenImage;
        return null;
    }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public Scope visit(FormalParameterRest n, String param){
        //System.out.println("FormalParamRest");
        n.f1.accept(this, null);
        return null;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public Scope visit(Type n, String param){
        //System.out.println("Type");
        Scope s = n.f0.accept(this, null);
        return s;
    }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public Scope visit(ArrayType n, String param){
        //System.out.println("ArrayType");
        return null;
    }

    /**
    * f0 -> "boolean"
    */
   public Scope visit(BooleanType n, String param){
    //System.out.println("BooleanType");
    return null;
   }

   /**
    * f0 -> "int"
    */
    public Scope visit(IntegerType n, String param){
        //System.out.println("integerType");
        return null;
    }
    
    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public Scope visit(Statement n, String param){
        //System.out.println("Statement");
        Scope str = n.f0.accept(this, null);
        //System.out.println("end Statement");
        return str;
    }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public Scope visit(Block n, String param){
        
        //System.out.println("block");
        n.f1.accept(this, null);
        return null;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public Scope visit(AssignmentStatement n, String param){
        //System.out.println("assignmentState");
        Scope str1 = n.f0.accept(this, null);
        Scope str2 = n.f2.accept(this, "assigning");

        if(hold.isValidRecord(currClass, str1.getName())){
            outp.addtext("[this+" + String.valueOf(hold.getRecordOffset(currClass, str1.getName()) + "] = " + str2.getName()));
        }
        else{
            //System.out.println("----- " + str2.getName());
            outp.addtext(str1.getName() + " = " + str2.getName());  
        }
        if(str2.getNull()){
            this.NullPrinter(str2.getVal());
            str2.changeNull(false);
        }
        //System.out.println("end assignmentState");
        return str1;
    }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
    public Scope visit(ArrayAssignmentStatement n, String param){
        //System.out.println("ArrayAssign");
        String ident = n.f0.f0.tokenImage;
        Scope exp1 = n.f2.accept(this, null);
        Scope exp2 = n.f5.accept(this, null);
        return exp1;
    }

    /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
    public Scope visit(IfStatement n, String param){
        //System.out.println("ifState");
        Scope str1 = n.f2.accept(this, null);

        outp.addtext("if0 " + str1.getVal() + " goto :" + varGen.getElse());

        outp.increaseIdent();
        Scope str2 = n.f4.accept(this, null);
        outp.addtext("goto :" + varGen.getEnd());
        outp.decreaseIdent();

        outp.addtext(varGen.getElse() + ":");

        outp.increaseIdent();
        //System.out.println("Enter if f6");
        Scope str3 = n.f6.accept(this, null);
        outp.decreaseIdent();

        outp.addtext(varGen.getEnd() + ":");

        varGen.incIfelse();
        varGen.incIfend();

        return null;
    }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public Scope visit(WhileStatement n, String param){
        //System.out.println("While");
        outp.addtext(varGen.getWhileTop() + ":");
        Scope exp = n.f2.accept(this, null);
        outp.addtext("if0 " + exp.getVal() + " goto :" + varGen.getWhileEnd());
        outp.increaseIdent();

        Scope state = n.f4.accept(this, null);
        outp.addtext("goto :" + varGen.getWhileTop());
        outp.decreaseIdent();


        outp.addtext(varGen.getWhileEnd() + ":");
        return null;
    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public Scope visit(PrintStatement n, String param){
        //System.out.println("Print");
        Scope str = n.f2.accept(this, "!newVar");
        //System.out.println("----->> " + str.getName() + " = " + str.getVal());
        if(str.isCall())
            outp.addtext("PrintIntS(" + str.getVal() + ")");
        else
            outp.addtext("PrintIntS(" + str.getName() + ")");
        return null;
    }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
    public Scope visit(Expression n, String param){
        //System.out.println("express");
        Scope str = n.f0.accept(this, param);
        if(str.getRef() && param != "assigning"){
            outp.addtext(varGen.getVar() + " = " + str.getName());
            str.changeName(varGen.getVar());
            str.changeVal(varGen.getVar());
            varGen.incVariableCount();
            str.changeIsCall(true);
        }
        if(param == "expressionParameters"){
            exprList += " " + str.getName();
        }
        
        //System.out.println("end express");
        return str;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(AndExpression n, String param){
            //System.out.println("AndExpress");
        String ss_else = "ss" + ssCount + "_else";
        String ss_end = "ss" + ssCount + "_end";
        ssCount++;

        Scope str1 = n.f0.accept(this, null);
        outp.addtext("if0 " + str1.getVal() + " goto :" + ss_else);
        outp.increaseIdent();
            Scope str2 = n.f2.accept(this, null);
            outp.addtext(varGen.getVar() + " = " + str2.getName());
            outp.addtext("goto :" + ss_end);
        outp.decreaseIdent();
        outp.addtext(ss_else + ":");
        outp.increaseIdent();
            outp.addtext(varGen.getVar() + " = 0");
        outp.decreaseIdent();
        outp.addtext(ss_end + ":");
        Scope pass_s = new Scope("&&&", varGen.getVar(), true, false);
        varGen.incVariableCount();
        return pass_s;
    }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(CompareExpression n, String param){
        //System.out.println("Compare");
        Scope LHS = n.f0.accept(this, null);
        Scope RHS = n.f2.accept(this, null);
        String comp = gen.LtS(LHS.getName(), RHS.getName());
        //Scope compare = "LtS(" + LHS + " " + RHS + ")";
        return new Scope(comp, "", true, false);
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(PlusExpression n, String param){
        //System.out.println("PlusEx");
        Scope LHS = n.f0.accept(this, null);
        Scope RHS = n.f2.accept(this, null);
        String add = gen.Add(LHS.getName(), RHS.getName());
        return new Scope(add, "", true, false);
    }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(MinusExpression n, String param){
        //System.out.println("Minusex");
        Scope LHS = n.f0.accept(this, null);
        Scope RHS = n.f2.accept(this, null);
        String str = gen.Sub(LHS.getName(), RHS.getName());
        //Scope compare = "LtS(" + LHS + " " + RHS + ")";
        //System.out.println("end minus");
        return new Scope(str, "", true, false);
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(TimesExpression n, String param){
        //System.out.println("TimesEx");
        Scope LHS = n.f0.accept(this, null);
        Scope RHS = n.f2.accept(this, null);
        String str = gen.MulS(LHS.getName(), RHS.getVal());
        Boolean ref = param == "assigning" ? false : true;
        return new Scope(str, "", ref, false);
        //return new Scope(str, "" , false, false);//made b1 to false because
                                        //in factorial it prints out twice
                                        //should be printed once in assign
    }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public Scope visit(ArrayLookup n, String param){
        //System.out.println("arraylook");
        Scope primExp1 = n.f0.accept(this, null);
        Scope primExp2 = n.f2.accept(this, null);
        return primExp1;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public Scope visit(ArrayLength n, String param){
        //System.out.println("Arraylen");
        Scope primExp = n.f0.accept(this, null);
        return primExp;
    }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
    public Scope visit(MessageSend n, String param){
        //System.out.println("MessageSend");
        Scope str1 = n.f0.accept(this, null); //primary
        Scope str2 = n.f2.accept(this, null); //identifier
        String var = varGen.getVar();
        varGen.incVariableCount();
        //varGen.incVariableCount();
        //outp.addtext("increment");
        
        Scope s = new Scope("", var, true, false);

        if(str1.getNull()){
            outp.addtext(gen.nullStr(str1.getVal(), varGen.getNull()));
            outp.increaseIdent();
            outp.addtext("Error(\"null pointer\")");
            str1.changeNull(false);
            outp.decreaseIdent();
            outp.addtext(varGen.getNull() + ":");
            varGen.incNull();
        }
        int offset = hold.getOffsetVtable(str1.getName(), str2.getName());
        outp.addtext(str2.getVal() + " = [" + str1.getVal() + "]" );
        outp.addtext(str2.getVal() + " = [" + str2.getVal() + "+" + offset + "]" );

        Scope str3 = n.f4.accept(this, "expressionParameters"); //expressionlist
        String messageSendCall = "call " + str2.getVal() + "(" + str1.getVal() + exprList + ")";
        //outp.addtext("call " + str2.getVal() + "(" + str1.getVal() + exprList + ")");
        /*if(str1.getNull()){
            
            varGen.incVariableCount();
           // outp.addtext("increment");
            int offset = hold.getOffsetVtable(str1.getName(), str2.getName());
            //System.out.println(">>> str1 n: "+ str1.getName() 
                + ", str2 n: " + str2.getName() + ", offset: " + offset);
            outp.addtext(str2.getVal() + " = [" + str1.getVal() + "]" );
            outp.addtext(str2.getVal() + " = [" + str2.getVal() + "+" + offset + "]" );

            //t.2 = call t.1(t.0 10) => need to get 10 of F4
            Scope temp = n.f4.accept(this, null);
            String temp_hold = s.getVal() + " = call " + str2.getVal() + "(" + str1.getVal();

            if(outp.getTemp().length() > 0){
                temp_hold += " " +  outp.getTemp() + ")";
            }
            else{
                temp_hold += ")";
            }
            outp.addtext(temp_hold);
             outp.addtext(s.getVal() + " = call " + str2.getVal() + "(" + str1.getVal() + " " 
                + outp.getTemp() + ")");  

                outp.clearTemp();
                s.changeIsCall(true); //only if its like Fac.Compute
                return s;
        }
        else{
            int offset = hold.getOffsetVtable(str1.getName(), str2.getName());

            //System.out.println(">>> str1 n: "+ str1.getName() 
                + ", str2 n: " + str2.getName() + ", offset: " + offset);
            outp.addtext(str2.getVal() + " = [" + str1.getVal() + "]" );
            outp.addtext(str2.getVal() + " = [" + str2.getVal() + "+" + offset + "]" );

            outp.addtext("testing: " + varGen.getVar());
            Scope temp = n.f4.accept(this, null);
            String s_temp;
            try{
                s_temp = temp.getVal();
            }catch(Exception e){
                s_temp = ""; 
            }
                                                                                    //temp.getVal()
            outp.addtext("testing2: " + str1.getVal() + " " + str2.getVal() + " "+ s_temp);//varGen.getVar
            Scope state = new Scope(s_temp, varGen.getVar(), false, false);
                                    //temp.getVal()
            outp.addtext("Trying to get: " + str1.getName());
            outp.addtext(state.getVal() + " = call " + str2.getVal() + "(" + str1.getVal() + " " 
                + s_temp + ")");//outp.getTemp
                //temp.getVal()
                //System.out.println("end messagesend");
                return state;

        }*/
        s.changeName(messageSendCall);
        exprList = "";
        return s;
    }

    /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    public Scope visit(ExpressionList n, String param){
        //System.out.println("ExpressionList");
        n.f0.accept(this, "expressionParameters");
        n.f1.accept(this, "expressionParameters");
        /* 
        Scope str = n.f0.accept(this, null);
        
        //outp.addtext("getting the name: " + str.getVal());
        //System.out.println("+++ExpressionList: " + str.getName());
        if(outp.getTemp().length() > 0){
            outp.tempString(" " + str.getName());
        }
        else{outp.tempString(str.getName());}

        Scope str2 = n.f1.accept(this, null);
        return str;
        */
        //System.out.println("end ExpressionList");
        return null;
    }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public Scope visit(ExpressionRest n, String param){
        //System.out.println("ExpressionRest");
        n.f1.accept(this, "expressionParameters");
        /*Scope str = n.f1.accept(this, null);
        //System.out.println("+++ExpressionRest: " + str.getName());
        if(outp.getTemp().length() > 0){
            outp.tempString(" " + str.getName());
        }
        else{outp.tempString(str.getName());}
        return str;*/
        return null;
    }

    /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
    public Scope visit(PrimaryExpression n, String param){
        //System.out.println("primaryEx");
        Scope str = n.f0.accept(this, param);
        
        //System.out.println("end primaryEx");
        return str;
    }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
    public Scope visit(IntegerLiteral n, String param){
        //System.out.println("IntegerLit: " + n.f0.tokenImage);
        Scope str = new Scope(n.f0.tokenImage);
        return str;
    }

    /**
    * f0 -> "true"
    */
    public Scope visit(TrueLiteral n, String param){
        //System.out.println("TrueLit");
        return new Scope("1");
    }

   /**
    * f0 -> "false"
    */
    public Scope visit(FalseLiteral n, String param){
        //System.out.println("FalseLit");
        return new Scope("0");
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public Scope visit(Identifier n, String param){
        //System.out.println("Ident: " + n.f0.tokenImage);
        String ident = n.f0.tokenImage;
        
        //System.out.println("PARAM AT IDENT: " + param);

        Scope str = new Scope(n.f0.tokenImage, varGen.getVar(), false, false);
        return str;
    }

   /**
    * f0 -> "this"
    */
    public Scope visit(ThisExpression n, String param){
        //System.out.println("ThisEx");
        
        return new Scope(currClass, "this", false, true);
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public Scope visit(ArrayAllocationExpression n, String param){
        isAllocArray = true;
        //System.out.println("ArrayAlloc");
        Scope exp = n.f3.accept(this, null);
        String var = varGen.getVar();
        varGen.incVariableCount();
        String arrayAlloc = var + " = call :AllocArray(" + exp.getName() + ")"; 
        outp.addtext(arrayAlloc);
        return new Scope(var);
    }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public Scope visit(AllocationExpression n, String param){
        //System.out.println("AllocEx");
        String var = varGen.getVar();

        Scope str = n.f1.accept(this, null); //identifier
        int len = hold.getOffsetRecord(str.getName()); //need to get the size of the class record

        outp.addtext(var + " = " + gen.HeapAllocZ(len));
        outp.addtext(gen.Array(var, str.getName()));
        
        
        varGen.incVariableCount();
        str.changeNull(true);
        str.changeVal(var);
        //System.out.println("end alloc");
        return str;
    }

    /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public Scope visit(NotExpression n, String param){
    //System.out.println("NotEx");
    Scope str = n.f1.accept(this, null);
    //System.out.println("Not Ex: " + str.getName() + " " + str.getVal());

    str.changeName("Sub(1 " + str.getVal() + ")");
    return str;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public Scope visit(BracketExpression n, String param){
        //System.out.println("Bracket");
        Scope str = n.f1.accept(this, null);
        return str;
    }

    //generators/printers
    public String varGenerator(int count){
        return "t." + count;
    }

    public String ifElseGenerator(Boolean end, int count){
        String gen = end ? "if" + count + "_end" : "if" + count + "_else";
        return gen;
    }

    public void NullPrinter(String s1){
        outp.addtext("if " + s1 + " goto :" + varGen.getNull());
        outp.increaseIdent();
        outp.addtext("Error(\"null pointer\")");
        outp.decreaseIdent();
        outp.addtext(varGen.getNull() + ":");
        varGen.incNull();
    }

    public void allocArrayPrinter(){
        outp.addtext("\nfunc AllocArray(size)");
        outp.increaseIdent();
        outp.addtext("bytes = MulS(size 4)");
        outp.addtext("bytes = Add(bytes 4)");
        outp.addtext("v = HeapAllocZ(bytes)");
        outp.addtext("[v] = size");
        outp.addtext("ret v");
        outp.decreaseIdent();
    }
}

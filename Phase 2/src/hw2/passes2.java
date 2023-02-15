package hw2;
import visitor.*;
import java.util.*;
import java.io.*;
import hw2.Types.Scope;
import hw2.prints.*;
import syntaxtree.*;

public class passes2 extends GJDepthFirst<Scope, Integer> {
    holders hold;
    CodeGenHelper gen = new CodeGenHelper();
    VariableGen varGen = new VariableGen();
    Output outp = new Output();
    String currClass = "";

    //1 = real id
    //2 = temp
    //0 = null
    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public passes2(Goal goal, holders h){
        System.out.println("Passes2");
        hold = h;
        goal.f0.accept(this, 0);
        System.out.println("end passes2");
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
    * f8 -> "int"
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
   public Scope visit(MainClass n, int argu) 
   {
    System.out.println("Maincl");
    outp.addtext("func Main()");
    outp.increaseIdent();
    n.f15.accept(this, 0);
    outp.addtext("ret");
    outp.decreaseIdent();
    System.out.println("End mainCl");
    varGen.resetCount();
    return null;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public Scope visit(TypeDeclaration n, int argu){
        System.out.println("Typedec");
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
   public Scope visit(ClassDeclaration n, int argu){
    System.out.println("Classdec");
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
    public Scope visit(ClassExtendsDeclaration n, int argu){
        System.out.println("ClassExtendDec");
        return null;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public Scope visit(VarDeclaration n, int argu){
    System.out.println("VarDec");
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
    public Scope visit(MethodDeclaration n, int argu){
        System.out.println("MethodDec");
        return null;
    }

    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public Scope visit(FormalParameterList n, int argu){
    System.out.println("FormalParamList");
    return null;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public Scope visit(FormalParameter n, int argu){
        System.out.println("FormalParam: " + n.f1.f0.tokenImage);
        return null;
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public Scope visit(FormalParameterRest n, int argu){
    System.out.println("FormalParamRest");
    return null;
   }    

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public Scope visit(Type n, int argu){
        System.out.println("Type");
        return null;
    }

    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public Scope visit(ArrayType n, int argu){
    System.out.println("ArrayType");
    return null;
   }

   /**
    * f0 -> "boolean"
    */
    public Scope visit(BooleanType n, int argu){
        System.out.println("BooleanType");
        return null;
    }

    /**
    * f0 -> "int"
    */
   public Scope visit(IntegerType n, int argu){
    System.out.println("integerType");
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
    public Scope visit(Statement n, int argu){
        System.out.println("Statement");
        return n.f0.accept(this, argu);
    }

    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public Scope visit(Block n, int argu){
    System.out.println("block");
    return null;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public Scope visit(AssignmentStatement n, int argu){
        System.out.println("assignmentState");
        return null;
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
   public Scope visit(ArrayAssignmentStatement n, int argu){
    System.out.println("ArrayAssign");
    return null;
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
    public Scope visit(IfStatement n, int argu){
        System.out.println("ifState");
        return null;
    }

    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public Scope visit(WhileStatement n, int argu){
    System.out.println("While");
    return null;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public Scope visit(PrintStatement n, int argu){
        System.out.println("Print");
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
   public Scope visit(Expression n, int argu){
    System.out.println("express");
    return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(AndExpression n, int argu){
        System.out.println("AndExpress");
        return null;
    }


    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public Scope visit(CompareExpression n, int argu){
    System.out.println("Compare");
    return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(PlusExpression n, int argu){
        System.out.println("PlusEx");
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public Scope visit(MinusExpression n, int argu){
    System.out.println("Minusex");
    return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public Scope visit(TimesExpression n, int argu) {
        System.out.println("TimesEx");
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public Scope visit(ArrayLookup n, int argu){
    System.out.println("arraylook");
    return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public Scope visit(ArrayLength n, int argu){
        System.out.println("Arraylen");
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public Scope visit(MessageSend n, int argu){
    System.out.println("MessageSend");
    return null;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    public Scope visit(ExpressionList n, int argu){
        System.out.println("ExpressionList");
        return null;
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public Scope visit(ExpressionRest n, int argu){
    System.out.println("ExpressionRest");
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
    public Scope visit(PrimaryExpression n, int argu){
        System.out.println("primaryEx");
        return null;
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
   public Scope visit(IntegerLiteral n, int argu){
    System.out.println("IntegerLit: " + n.f0.tokenImage);
    return null;
   }

   /**
    * f0 -> "true"
    */
    public Scope visit(TrueLiteral n, int argu){
        System.out.println("TrueLit");
        return null;
    }

    /**
    * f0 -> "false"
    */
   public Scope visit(FalseLiteral n, int argu){
    System.out.println("FalseLit");
    return null;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
    public Scope visit(Identifier n, int argu){
        System.out.println("Ident: " + n.f0.tokenImage);
        return null;
    }

    /**
    * f0 -> "this"
    */
   public Scope visit(ThisExpression n, int argu) {
    System.out.println("ThisEx");
    return null;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public Scope visit(ArrayAllocationExpression n, int argu){
        System.out.println("ArrayAlloc");
        return null;
    }

    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public Scope visit(AllocationExpression n, int argu){
    System.out.println("AllocEx");
    return null;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
    public Scope visit(NotExpression n, int argu) {
        System.out.println("NotEx");
        return null;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public Scope visit(BracketExpression n, int argu){
    System.out.println("Bracket");
    return null;
   }

   public void NullPrinter(String s1){
    outp.addtext("if " + s1 + " goto :" + varGen.getNull());
    outp.increaseIdent();
    outp.addtext("Error(\"null pointer\")");
    outp.decreaseIdent();
    outp.addtext(varGen.getNull());
    varGen.incNull();
   }
}

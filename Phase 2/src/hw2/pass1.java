package hw2;

import visitor.*;
import hw2.*;
import hw2.Types.Scope;
import syntaxtree.*;
import java.util.*;
import java.io.*;

public class pass1 extends GJNoArguDepthFirst<String>{
    
    Map<String,holder> hw2 = new HashMap<>();
    String supClass; //holds the current supper class
    holder hold = new holder();
    LinkedHashMap<String, String> mapper = new LinkedHashMap<>();
    int position = 0;
    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public pass1(Goal goal){
        //-System.out.println("test 1");
        goal.f1.accept(this);
        //-System.out.println("Re-entered Goal");

        /* if(mapper != null){
            hold.MethodAdder(supClass, mapper);
            mapper.clear();
        } */
        //hold.print();
        //printer p = new printer(hold);
        //String out = p.print();
        //hold.print();
        System.out.println("checking1 ");
        //pass2 pass_2 = new pass2(goal, hold);
        //System.out.println("Vt2 size in element " + hold.vt2_size("Element"));
        System.out.println("checking2");

        /* try{
            FileWriter fw = new FileWriter("output.vapor");
            fw.write(out);
            fw.close();
        }
        catch(IOException e){
            //-System.out.println("caught exception");
        } */
        //-System.out.println("----\n------\n");
        //-System.out.println(mapper);
        //hold.print();
        

        ////-System.out.println(out);
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
   public String visit(MainClass n){
    //-System.out.println("Maincl");
    return null;

   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public String visit(TypeDeclaration n) {
        //-System.out.println("Typedec");
        
        position = 0;
        n.f0.accept(this);
        hold.MethodAdder(supClass, mapper);
        //-System.out.println("Class: " + supClass);
        //-System.out.println(mapper);
        mapper.clear();


        //-System.out.println("Exit Typedec");
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
   public String visit(ClassDeclaration n){
    //-System.out.println("Classdec");
    supClass = n.f1.accept(this);
    n.f3.accept(this);
    hw2.put(supClass, new holder());
    n.f4.accept(this);

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
    public String visit(ClassExtendsDeclaration n){
        //-System.out.println("ClassExtendDec");
        String curr = n.f1.accept(this);
        supClass = curr;
        n.f5.accept(this);
        n.f6.accept(this);
        String str = n.f3.accept(this);
        hold.addExtendClass(curr, str); //class B extends A: curr(B), str(A)
        //-System.out.println("Exit ClassExtendDec");
        return null;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public String visit(VarDeclaration n){
    System.out.println("VarDec");
    String str1 = n.f0.accept(this);
    String str2 = n.f1.accept(this);
    System.out.println(str1 + " === " + str2);
    hold.addObject(supClass, str2, str1);
    System.out.println("End VarDec");
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
    public String visit(MethodDeclaration n){
        //-System.out.println("MethodDec"); 
        String str1 = n.f1.accept(this);
        String str2 = n.f2.accept(this);
        //System.out.println(str1 + " " + str2 + " == " + supClass);
        hold.addMethod(supClass, str2, str1);
        mapper.put(str2, supClass);
        return null;
    }

    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public String visit(FormalParameterList n){
    //-System.out.println("FormalParamList");
    return null;
   }

   public String visit(FormalParameter n){
    //-System.out.println("FormalParam");
    return null;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public String visit(FormalParameterRest n){
        //-System.out.println("FormalParamRest");
        return null;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public String visit(Type n){
    //-System.out.println("Type");
    String s = n.f0.accept(this);
    return s;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public String visit(ArrayType n){
        //-System.out.println("ArrayType");
        return null;
    }

    /**
    * f0 -> "boolean"
    */
   public String visit(BooleanType n){
    //-System.out.println("BooleanType");
    return "boolean";
   }

   /**
    * f0 -> "int"
    */
    public String visit(IntegerType n){
        //-System.out.println("integerType");
        return "int";
    }
    
    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public String visit(Statement n){
    //-System.out.println("Statement");
    return null;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public String visit(Block n){
        //-System.out.println("block");
        return null;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public String visit(AssignmentStatement n){
    //-System.out.println("assignmentState");
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
    public String visit(ArrayAssignmentStatement n){
        //-System.out.println("ArrayAssign");
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
   public String visit(IfStatement n){
    //-System.out.println("ifState");
    return null;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public String visit(WhileStatement n){
        //-System.out.println("While");
        return null;
    }

    /**
    * f0 -> "//-System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public String visit(PrintStatement n){
    //-System.out.println("Print");
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
    public String visit(Expression n){
        //-System.out.println("express");
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public String visit(AndExpression n){
    //-System.out.println("AndExpress");
    return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public String visit(CompareExpression n){
        //-System.out.println("Compare");
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public String visit(PlusExpression n){
    //-System.out.println("PlusEx");
    return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public String visit(MinusExpression n){
        //-System.out.println("Minusex");
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public String visit(TimesExpression n){
    //-System.out.println("TimesEx");
    return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public String visit(ArrayLookup n){
        //-System.out.println("arraylook");
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public String visit(ArrayLength n){
    //-System.out.println("Arraylen");
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
    public String visit(MessageSend n){
        //-System.out.println("MessageSend");
        return null;
    }

    /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public String visit(ExpressionList n){
    //-System.out.println("ExpressionList");
    return null;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public String visit(ExpressionRest n){
        //-System.out.println("ExpressionRest");
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
   public String visit(PrimaryExpression n){
    //-System.out.println("primaryEx");
    return null;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
    public String visit(IntegerLiteral n){
        //-System.out.println("IntegerLit");
        return null;
    }

    /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n){
    //-System.out.println("TrueLit");
    return "boolean";
   }

   /**
    * f0 -> "false"
    */
    public String visit(FalseLiteral n){
        //-System.out.println("FalseLit");
        return "boolean";
    }

    /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Identifier n){
    //-System.out.println("Ident: " + n.f0.tokenImage);
    return (String)n.f0.tokenImage;
   }

   /**
    * f0 -> "this"
    */
    public String visit(ThisExpression n){
        //-System.out.println("TimesEx");
        return null;
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public String visit(ArrayAllocationExpression n){
    //-System.out.println("ArrayAlloc");
    return null;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public String visit(AllocationExpression n){
        //-System.out.println("AllocEx");
        return null;
    }

    /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public String visit(NotExpression n){
    //-System.out.println("NotEx");
    return null;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public String visit(BracketExpression n){
        //-System.out.println("Bracket");
        return null;
    }
}

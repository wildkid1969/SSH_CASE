package my.test;

import java.util.ArrayList;
import java.util.List;

import com.rollingStones.utils.CommonUtils;

public class TestStatic {
    private static int id;
    private static String name;
    
    public static int getId() {
        return id;
    }
    public static void setId(int id) {
        TestStatic.id = id;
    }
    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        TestStatic.name = name;
    }
    
    
    @Override
    public String toString() {
        return "id:"+id+","+"name:"+name;
    }
    
    
    public static void main(String[] args) {
        List<TestStatic> list = new ArrayList<TestStatic>();
        
        for(int i=0;i<4;i++){
            TestStatic t = new TestStatic();
            t.setId(i+1);
            t.setName("Keith Richard"+(i+1));
            
            TestStatic.setId(i+1);//由于id 与 Name 是静态变量，其值只能有一份， 所以，每一次的set 都是一次覆盖，最后当然是被最后一次的“4”给覆盖了
            
            list.add(t);
        }
        
        System.out.println(list);
        
        System.out.println(CommonUtils.factorial(4));
        
        int i=3;
        char a ='A',b='B',c='C';
        hanio(i,a,b,c);
    }
    
    public static void hanio(int n,char one,char two,char three){
          if(n==1)
              System.out.println("1移动"+n+"号盘子从"+one+"到"+three);
         else{
             hanio(n-1,one,three,two);//把上面n-1个盘子从a借助b搬到c
             System.out.println("2移动"+n+"号盘子从"+one+"到"+three);//紧接着直接把n搬动c
             hanio(n-1,two,one,three);//再把b上的n-1个盘子借助a搬到c
         }
     }
    
    
}

package com.blog.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.blog.util.CharStreamImpl;

public class AutoJava {
	private StringBuilder sb = new StringBuilder();
	private Context context = new Context();
	static Map<String, String> key = new HashMap<>();
	static Map<String, String> commond = new HashMap<>();
	private Stack<String> spaceStack = new Stack<>();
	private boolean isSpace = true;
	public static void main(String[] args) {
		commond.put("public", "001");
		commond.put("protected", "001");
		commond.put("private", "001");
		commond.put(" ", "000");
		commond.put("static", "005");
		commond.put("void", "006");
		commond.put("String", "006");commond.put("sort", "006");commond.put("byte", "006");commond.put("char", "006");
		commond.put("int", "006");commond.put("long", "006");commond.put("boolean", "006");
		//00 public  01 protected  02 private
		//03 void 04 String 05   其他各种类型 
		key.put("00", "public");
		key.put("01", "protected");
		key.put("02", "private");
		key.put("03", "void");
		key.put("04", "sort");
		key.put("05", "byte");
		key.put("06", "char");
		key.put("07", "int");
		key.put("08", "long");
		key.put("09", "String");
		try {
			AutoJava a = new AutoJava();
			a.dod();
			System.out.println(a.toString());
			CharStreamImpl stream = new CharStreamImpl("d://Testtest.java");
			stream.write(a.toString());
			stream.close();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public void dod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		context.setStrategy(new ClazzStrategy());
		context.setNowDo("编写类");
		ClaTask task = new ClaTask();
		task.setConfig(new ClaConfig());
		
		task.varTaskList.add(new VarTask());
		task.varTaskList.add(new VarTask());
		
		task.MetTaskList.add(new MetTask());
		task.MetTaskList.add(new MetTask());
		context.setTask(task);
		
		core(task);
	}
	public void core(Task task) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		context.setCurrentTask(task);
		String[] commom = context.getStrategy().getMemory();
		for(int i = 0, len = commom.length; i < len; i++){
			String commomItem = commom[i];
			Class<?> clazz = AutoJava.class;
			Method m = clazz.getMethod("do"+commomItem);
			m.invoke(this);
		}
	}
	public void do000(){
		if(isSpace)
			ouput(" ");
		else 
			isSpace = true;
	}
	public void do001(){
		ouput(key.get(context.getCurrentConf().getMod()));
	}
	public void do002(){
		ouput("class");
	}
	public void do003(){
		ouput(context.getCurrentConf().getName());
	}
	public void do004(){
		if(!spaceStack.empty()){
			spaceStack.add(spaceStack.peek()+"\t");
		}else {
			spaceStack.add("\t");
		}
	
		ouput("{"+System.lineSeparator());
	}
	public void do005(){
		if(context.getCurrentConf().isState())
			ouput("static");
		else isSpace = false;
	}
	public void do006(){
		ouput(key.get(context.getCurrentConf().getResultType()));
	}
	public void do007(){
		ouput(context.getCurrentConf().getName());
	}
	public void do008(){
		ouput("(");
	}
	public void do009(){
		ouput("String arg");
	}
	public void do010(){
		ouput(")");
	}
	public void do011(){
		if(!spaceStack.empty())
			ouput(spaceStack.peek());
		ouput("// 代码行"+System.lineSeparator());
	}
	public void do012(){
		if(!spaceStack.empty()){
			spaceStack.pop();
			if(!spaceStack.empty()) ouput(spaceStack.peek());
		}
		ouput("}"+System.lineSeparator());
	}
	public void do013(){
		List<Task> list = ((ClaTask) context.getTask()).getVarTaskList();
		context.setStrategy(new VariableStrategy());
		for(Task item : list){
			if(!spaceStack.empty())
				ouput(spaceStack.peek());
			try {
				core(item);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	public void do014(){
		List<Task> list = ((ClaTask) context.getTask()).getMetTaskList();
		context.setStrategy(new MethodStrategy());
		for(Task item : list){
			if(!spaceStack.empty())
				ouput(spaceStack.peek());
			try {
				core(item);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	public void do015(){
		ouput(context.getCurrentConf().getName());
	}
	public void do016(){
		ouput(";"+System.lineSeparator());
	}
	public void ouput(String line){
		sb.append(line);
	}
	public String toString(){
		return sb.toString();
	}
}

class Context{
	private Strategy strategy;
	private String nowDo;
	private Task task;
	private Task currentTask;

	public Context(){
		this.setCurrentTask(task);
	}
	
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	 
	public String getNowDo() {
		return nowDo;
	}

	public void setNowDo(String nowDo) {
		this.nowDo = nowDo;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}
	public Config getCurrentConf(){
		return this.getCurrentTask().getConfig();
	}
	
}


// 任务： 声明一个变量是一个任务， 写一个函数是一个任务， 写一个类是一个任务
class Task{
	private Config config;		// 任务的具体配置信息
	private String desc;		// 当前任务 具体文字描述
	private String commandCode; // 任务命令编码
	
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}
	
}
class VarTask extends Task{
	public VarTask(){
		this.setConfig(new VarConfig());
	}
}
class MetTask extends Task{
	public MetTask(){
		this.setConfig(new MetConfig());
	}
}
class ClaTask extends Task{
	List<Task> varTaskList = new ArrayList<>();
	List<Task> MetTaskList = new ArrayList<>();
	public List<Task> getVarTaskList() {
		return varTaskList;
	}
	public void setVarTaskList(List<Task> varTaskMap) {
		this.varTaskList = varTaskMap;
	}
	public List<Task> getMetTaskList() {
		return MetTaskList;
	}
	public void setMetTaskList(List<Task> metTaskMap) {
		MetTaskList = metTaskMap;
	}
}

// 一个任务的具体配置信息
class Config{
	private boolean isState = false;
	private String mod = "00"; //00 public  01 protected  02 private
	private String resultType = "09"; //03 void 04 String 05   其他各种类型 
	private String name; 
	public boolean isState() {
		return isState;
	}
	public void setState(boolean isState) {
		this.isState = isState;
	}
	public String getMod() {
		return mod;
	}
	public void setMod(String mod) {
		this.mod = mod;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	} 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
class VarConfig extends Config{
	public VarConfig(){
		this.setName("a");
	}
}
class MetConfig extends Config{
	public MetConfig(){
		this.setName("method");
	}
}
class ClaConfig extends Config{
	public ClaConfig(){
		this.setName("Test");
	}
}

interface Strategy{
	String[] getMemory();
}
class VariableStrategy implements Strategy{
	
	private String[] m;
	@Override
	public String[] getMemory() {
		// TODO Auto-generated method stub
		if(m == null) m = new String[]{"001", "000", "005", "000", "006", "000", "015", "016"};
		return m;
	}
	//mod 01 02 03 04
	public boolean checkMod(String mod){
		if("01".equals(mod)){
			return true;
		}
		return false;
	}  
	
}

class MethodStrategy implements Strategy{
	
	private String[] m;
	@Override
	public String[] getMemory() {
		// TODO Auto-generated method stub
		if(m == null) m = new String[]{"001", "000", "005", "000", "006", "000", "007", "000", "008", "009", "010", "004", "011", "012"};
		return m;
	}
	
}

class ClazzStrategy implements Strategy{
	
	private String[] m;
	@Override
	public String[] getMemory() {
		// TODO Auto-generated method stub
		if(m == null) m = new String[]{"001", "000", "002", "000", "003", "000", "004", "013", "014", "012"};
		return m;
	}
	
}
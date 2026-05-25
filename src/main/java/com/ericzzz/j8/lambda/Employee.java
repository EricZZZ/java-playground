package com.ericzzz.j8.lambda;

import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    private int age;
    private String city;

    public Employee(int id, String name, String department, double salary, int age, String city) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.age = age;
        this.city = city;
    }

    // getter方法
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', department='" + department +
                "', salary=" + salary + ", age=" + age + ", city='" + city + "'}";
    }

    public static void main(String[] args) {
        // 创建员工列表
        List<Employee> employees = Arrays.asList(
                new Employee(1, "张三", "IT", 15000, 28, "北京"),
                new Employee(2, "李四", "财务", 8000, 35, "上海"),
                new Employee(3, "王五", "IT", 18000, 32, "北京"),
                new Employee(4, "赵六", "市场", 12000, 26, "深圳"),
                new Employee(5, "钱七", "财务", 9500, 41, "上海"),
                new Employee(6, "孙八", "市场", 11000, 29, "北京"),
                new Employee(7, "周九", "IT", 16000, 37, "深圳"),
                new Employee(8, "吴十", "人事", 7000, 24, "上海"),
                new Employee(9, "郑十一", "IT", 19000, 45, "北京"),
                new Employee(10, "王十二", "市场", 13000, 31, "深圳"));

        // 找出所有在北京工作的员工
        List<Employee> employees2 = employees.stream().filter(e -> e.getCity().equals("北京"))
                .collect(Collectors.toList());
        System.out.println(employees2);

        // 获取所有员工的名字列表
        List<String> names = employees.stream().map(Employee::getName).collect(Collectors.toList());
        System.out.println(names);

        // 按工资从高到低排序员工
        List<Employee> employees3 = employees.stream()
                .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                .collect(Collectors.toList());
        System.out.println(employees3);

        // 打印所有IT部门的员工信息
        employees.stream().filter(e -> e.getDepartment().equals("IT")).forEach(System.out::println);

        // 计算所有员工的平均工资
        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        System.out.println("计算所有员工的平均工资:" + averageSalary);

        // 按部门分组员工
        Map<String, List<Employee>> employeesByDepartment = employees.stream()
                .collect(groupingBy(Employee::getDepartment));
        System.out.println(employeesByDepartment);

        // 找出第一个工资大于15000的员工
        Optional<Employee> employee4 = employees.stream().filter(e -> e.getSalary() > 15000)
                .findFirst();
        System.out.println(employee4);

        // 计算财务部门员工的总工资
        double averageSalary2 = employees.stream().filter(e -> e.getDepartment().equals("财务"))
                .mapToDouble(Employee::getSalary).sum();
        System.out.println("计算财务部门员工的总工资:" + averageSalary2);

        // 将所有员工的名字用逗号连接成一个字符串
        String namestr = employees.stream().map(Employee::getName).collect(Collectors.joining(","));
        System.out.println("所有员工的名字列表:" + namestr);

        // 找出年龄大于30且工资高于12000的员工
        List<Employee> employees5 = employees.stream().filter(e -> e.getAge() > 30).filter(e -> e.getSalary() > 12000)
                .collect(Collectors.toList());
        System.out.println(employees5);

        // 先按城市分组，再按部门分组
        Map<String, Map<String, List<Employee>>> employeesByDepartmentAndCity = employees.stream()
                .collect(groupingBy(Employee::getCity, groupingBy(Employee::getDepartment)));
        System.out.println(employeesByDepartmentAndCity);

        // 并行流计算IT部门员工的平均年龄
        double averageAge = employees.stream().parallel().filter(e -> e.getDepartment().equals("IT"))
                .mapToInt(Employee::getAge).average().orElse(0.0);
        System.out.println("使用并行流计算所有IT部门员工的平均年龄:" + averageAge);

        // 先按部门排序，部门相同再按工资降序排序
        List<Employee> employees6 = employees.stream()
                .sorted(Comparator.comparing(Employee::getDepartment)
                        .thenComparing((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary())))
                .collect(Collectors.toList());
        System.out.println("先按部门排序，部门相同再按工资降序排序:" + employees6);

        // 查找年龄最大的员工，如果不存在则返回默认员工
        Employee employee = employees.stream().max(Comparator.comparing(Employee::getAge))
                .orElse(new Employee(0, "默认员工", "默认部门", 0, 0, "默认城市"));
        System.out.println("查找年龄最大的员工，如果不存在则返回默认员工:" + employee);

        // 创建一个Map，key为部门，value为该部门员工的名字列表
        Map<String, List<String>> departmentToNames = employees.stream()
                .collect(groupingBy(Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println("创建一个Map，key为部门，value为该部门员工的名字列表:" + departmentToNames);

        // 统计市场部员工的工资信息
        DoubleSummaryStatistics marketStats = employees.stream().filter(e -> e.getDepartment().equals("市场"))
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println("市场部工资统计:");
        System.out.println("   数量: " + marketStats.getCount());
        System.out.println("   平均: " + marketStats.getAverage());
        System.out.println("   最高: " + marketStats.getMax());
        System.out.println("   最低: " + marketStats.getMin());
        System.out.println("   总和: " + marketStats.getSum());

        // 获取所有员工所在城市的列表（去重）
        List<String> cityNames = employees.stream().map(Employee::getCity).distinct().collect(Collectors.toList());
        System.out.println("获取所有员工所在城市的列表（去重）:" + cityNames);

        // 检查是否所有IT部门员工的工资都高于10000
        boolean allITAbove10000 = employees.stream().filter(e -> e.getDepartment().equals("IT"))
                .allMatch(e -> e.getSalary() > 10000);
        System.out.println("检查是否所有IT部门员工的工资都高于10000:" + allITAbove10000);

        // 创建包含姓名和税后工资的列表
        List<String> nameAndNetSalary = employees.stream().map(e -> e.getName() + ": " + (e.getSalary() * 0.8))
                .collect(Collectors.toList());
        System.out.println("创建包含姓名和税后工资的列表:" + nameAndNetSalary);
    }
}

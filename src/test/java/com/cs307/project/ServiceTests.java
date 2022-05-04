package com.cs307.project;

import com.cs307.project.entity.*;
import com.cs307.project.service.IService;
import com.cs307.project.service.ex.ServiceException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTests {
    @Autowired
    private IService iService;

    @BeforeAll
    public static void init() {
        ClassLoader classLoader = ServiceTests.class.getClassLoader();
        try {
            Class<?> loadClass = classLoader.loadClass("com.cs307.project.utils.Loader");
            Method method = loadClass.getMethod("main", String[].class);
            method.invoke(null, new Object[]{new String[]{}});
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Init");
    }

    @Test
    public void stockInTest() {//2
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task1_in_stoke_test_data_publish.csv")))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                StockIn stock = new StockIn();
                readStockIn(stock, line);
                try {
                    iService.stockIn(stock);
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
                System.out.println(++cnt);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void placeOrderTest() {//3
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task2_test_data_publish.csv")))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                PlaceOrder placeOrder = new PlaceOrder();
                readPlaceOrder(placeOrder, line);
                try {
                    iService.placeOrder(placeOrder);
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
                System.out.println(++cnt);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateOrderTest() {//4
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task34_update_test_data_publish.tsv")))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                String[] content = line.split("\t");
                String[] dateArr = content[4].split("-");
                int year = Integer.parseInt(dateArr[0]);
                int month = Integer.parseInt(dateArr[1]) - 1;
                int day = Integer.parseInt(dateArr[2]);
                Calendar edc = Calendar.getInstance();
                edc.set(year, month, day);
                Date edd = edc.getTime();
                dateArr = content[5].split("-");
                year = Integer.parseInt(dateArr[0]);
                month = Integer.parseInt(dateArr[1]) - 1;
                day = Integer.parseInt(dateArr[2]);
                Calendar lc = Calendar.getInstance();
                lc.set(year, month, day);
                Date ld = edc.getTime();
                System.out.println(++cnt);
                try {
                    iService.updateOrder(content[0], content[1], content[2], Integer.parseInt(content[3]), edd, ld);
                }catch (ServiceException e){
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteOrderTest() {//5
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task34_delete_test_data_publish.tsv")))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                String[] content = line.split("\t");
                System.out.println(++cnt);
                try {
                    iService.deleteOrder(content[0], content[1], Integer.parseInt(content[2]));
                }catch (ServiceException e){
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllStaffCountTest() {//6
        List<StaffCount> staffCounts = iService.getAllStaffCount();
        for (StaffCount sc : staffCounts) System.out.println(sc);
    }

    @Test
    public void getContractNumberTest() {//7
        Integer cnt = iService.getContractCount();
        System.out.println(cnt);
    }

    @Test
    public void getOrderCountTest() {//8
        Integer cnt = iService.getOrderCount();
        System.out.println(cnt);
    }

    @Test
    public void getNeverSoldProductCountTest(){//9
        Integer cnt = iService.getNeverSoldProductCount();
        System.out.println(cnt);
    }

    @Test
    public void getFavoriteProductModelTest(){//10
        FavoriteModel fm = iService.getFavoriteProductModel();
        System.out.println(fm);
    }
    @Test
    public void getgetAvgStockByCenter(){
        List<AvgStockByCenter> list = iService.getgetAvgStockByCenter();
        for (AvgStockByCenter a:
             list) {
            System.out.println(a.toString());
        }
    }

    public void readPlaceOrder(PlaceOrder placeOrder, String line) {
        String[] content = line.split(",");
        placeOrder.setContractNum(content[0]);
        placeOrder.setEnterprise(content[1]);
        placeOrder.setProductModel(content[2]);
        placeOrder.setQuantity(Integer.parseInt(content[3]));
        placeOrder.setContractManager(content[4]);
        String[] dateArr = content[5].split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]) - 1;
        int day = Integer.parseInt(dateArr[2]);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date date = c.getTime();
        placeOrder.setContractDate(date);
        dateArr = content[6].split("-");
        year = Integer.parseInt(dateArr[0]);
        month = Integer.parseInt(dateArr[1]) - 1;
        day = Integer.parseInt(dateArr[2]);
        c = Calendar.getInstance();
        c.set(year, month, day);
        date = c.getTime();
        placeOrder.setEstimatedDeliveryDate(date);
        dateArr = content[7].split("-");
        year = Integer.parseInt(dateArr[0]);
        month = Integer.parseInt(dateArr[1]) - 1;
        day = Integer.parseInt(dateArr[2]);
        c = Calendar.getInstance();
        c.set(year, month, day);
        placeOrder.setLodgementDate(date);
        placeOrder.setSalesmanNum(content[8]);
        placeOrder.setContractType(content[9]);
    }

    public void readStockIn(StockIn stock, String line) {
        String[] content = line.split(",");
        stock.setId(Integer.parseInt(content[0]));
        if (content.length == 7) {
            stock.setSupplyCenter(content[1]);
            stock.setProductModel(content[2]);
            stock.setSupplyStaff(content[3]);
            String[] dateArr = content[4].split("/");
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]) - 1;
            int day = Integer.parseInt(dateArr[2]);
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            Date date = c.getTime();
            stock.setDate(date);
            stock.setPurchasePrice(Integer.parseInt(content[5]));
            stock.setQuantity(Integer.parseInt(content[6]));
        } else {
            stock.setSupplyCenter(content[1] + "," + content[2]);
            stock.setProductModel(content[3]);
            stock.setSupplyStaff(content[4]);
            String[] dateArr = content[5].split("/");
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]) - 1;
            int day = Integer.parseInt(dateArr[2]);
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            Date date = c.getTime();
            stock.setDate(date);
            stock.setPurchasePrice(Integer.parseInt(content[6]));
            stock.setQuantity(Integer.parseInt(content[7]));
        }
    }

}
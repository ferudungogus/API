package com.techproed.day07;

import com.techproed.testBase.jsonPlaceHolderTestBase;
import com.techproed.testData.JsonPlaceHolderTestData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class GetRequest11TestBase extends jsonPlaceHolderTestBase{
    /*
https://jsonplaceholder.typicode.com/todos/2 url ‘ine istek gönderildiğinde,
Dönen response un
Status kodunun 200, dönen body de,
"completed": değerinin false
"title”: değerinin “quis ut nam facilis et officia qui”
"userId" sinin 1 ve
header değerlerinden
"Via" değerinin “1.1 vegur” ve
"Server" değerinin “cloudflare” olduğunu test edin…
*/
    /*
    url oluştur
    --expected data
    request gönder
   -- actual data
    assertion
     */
    @Test
    public void test(){
        spec01.pathParams("parametre1","todos",
                "parametre2",2);
        JsonPlaceHolderTestData expectedObje=new JsonPlaceHolderTestData();
        HashMap<String,Object> expectedData= (HashMap<String, Object>) expectedObje.setupTestData();
        System.out.println(expectedData);



        Response response=given().
                accept("application/json").
                spec(spec01).
                when().
                get("/{parametre1}/{parametre2}");
        response.prettyPrint();
        //1. yöntem Mathers class ile assertion işlemi yaptık
        response.then().assertThat().statusCode((Integer)expectedData.get("statusCode")).
                headers("via", equalTo(expectedData.get("via")),
                        "Server",equalTo(expectedData.get("Server"))).
                body("userId",equalTo(expectedData.get("userId")),
                        "title",equalTo(expectedData.get("title")),
                        "completed",equalTo(expectedData.get("completed")));
        //2. yöntem
        JsonPath jsonPath=response.jsonPath();
        Assert.assertEquals(expectedData.get("statusCode"),response.statusCode());
        Assert.assertEquals(expectedData.get("via"),response.getHeader("via"));
        Assert.assertEquals(expectedData.get("Server"),response.getHeader("Server"));
        Assert.assertEquals(expectedData.get("userId"),jsonPath.getInt("userId"));
        Assert.assertEquals(expectedData.get("title"),jsonPath.getString("title"));
        Assert.assertEquals(expectedData.get("completed"),jsonPath.getBoolean("completed"));
        //3. yöntem  deserialization
        //   --object mapper
        //   --pojo class ile birlite map
        HashMap<String,Object>actualData=response.as(HashMap.class);
        System.out.println(actualData);
        Assert.assertEquals(expectedData.get("userId"),actualData.get("userId"));
        Assert.assertEquals(expectedData.get("title"),actualData.get("title"));
        Assert.assertEquals(expectedData.get("completed"),actualData.get("completed"));


    }
}

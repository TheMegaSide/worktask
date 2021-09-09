package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestCont {
    @Autowired
    private MoexClient moexService;

    @GetMapping("/")
    public List<MoexResponse> test() {
        return moexService.getMoex();
    }
    @GetMapping("/t")
    public MoexResponse test2() {
        ArrayList<DataObj> strings = new ArrayList<>();
        ArrayList<String> stringArrayList=new ArrayList<>();
        stringArrayList.add("1");
        strings.add(new DataObj(stringArrayList));
        return new MoexResponse(new History(strings));
    }


}
@FeignClient(url="http://iss.moex.com/iss/history/engines/stock/markets/shares",name="MoexClient")
interface MoexClient {
     @GetMapping("/securities.json")
     ArrayList<MoexResponse> getMoex();
}

@Data
@AllArgsConstructor
class MoexResponse {
    @JsonProperty("history")
    History history;
}

@Data
@AllArgsConstructor
class History {
    @JsonProperty("data")
    ArrayList<DataObj> data;
}

@Data
@AllArgsConstructor
class DataObj {

    ArrayList<String> ticker;
}



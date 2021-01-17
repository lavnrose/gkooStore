package util;

import java.util.HashMap;
import agencyEntities.CategoryUsage;

public class GrobalDefined {
 public static final boolean TRANSLATE = false;
    
    public static final HashMap<String, String> grobalDefinedUsage = new HashMap<>() {
        {
            put("아이케어", "매일 아침, 저녁으로 피부결을 정돈후에 주름이 있는 눈가에 적당량을 덜어 부드럽게 펴 발라줍니다. 눈가외에 입주위, 미간 등과 같이 주름이 생기기 쉬운 부위에도 적당량을 발라주면 좋습니다.");
            put("아이패드", "포장지를 벗겨내서 각 패드를 눈위에 올려놓고 10분정도 뒤에 벗겨내줍니다.");
            
            //입욕제
            put("사해소금", "전신욕, 반신욕, 족욕에 모두 사용가능합니다. 따뜻한 온수에 적댱량을 덜어 녹여줍니다. 전신욕 3스푼, 반신옥 2스푼, 세안시 반스푼 정도가 적당하나 기호에 맞춰 조절해주시면 좋습니다.");

            put("bb크림", "");
            
            put("티트리오일", "여드름 부위에 면봉을 이용해서 가볍게 발라줍니다. 샴푸시 소량을 같이 사용하실수 있습니다. 세안시 몇방물 첨가해서 사용할수도 있습니다. 목욕이나 족욕시에도 적당량을 덜어 사용하시면 좋습니다.");
            
            put("수분크림", "세안후 또는 아침 저녁으로 기초단계 이후에 적당량을 덜어서 얼굴에 펴 발라 흡수시켜줍니다.");
            put("나이트크림", "1)나이트크림을 소량 덜어내서 얼굴 온도에 맞게 체온으로 살짝 데워주면 흡수에 도움을 줄수 있습니다. 2)손으로 제품을 데콜테와 목에 고르게 발라줍니다. 3)지압법을 사용하면 혈액순환을 자극해 피부에 광채를 더해주고 탄텩유지에 도움을 줄수 있습니다.");
        
            put("고체샴푸", "물기가 있는 머리카락에 바로 고체샴푸를 문질러 거품을 내거나 물기있는 손으로 거품을 내서 머리카락에 마사지하듯 전체적으로 샴푸합니다. 그후에 깨끗한물로 행궈줍니다.");
        }
    };
    
    //Manual
    public static final HashMap<String, CategoryUsage> categoryUsageManual = new HashMap<>() {
        {
            
            put("1", new CategoryUsage("클렌징 비누", "물기있는 손에 적당량을 덜어 마사지하듯 문지른 후 미온수로 헹구어 내세요."));
            put("2", new CategoryUsage("입욕제", ""));
            //샤월젤
            put("3", new CategoryUsage("바디클렌져", "적당량을 바디스폰지나 바디솔, 타월에 묻혀 거품을 낸 후 전신에 마사지 하듯이 문질러주세요. 노폐물이 제거되면 물로 깨끗히 씻어주세요."));
            put("4", new CategoryUsage("바디로션", "샤워후 물기가 약간 남아있을때 바디로션 적당량을 손에 덜어 필요한 부위에 고르게 발라주면 좋습니다."));
            put("5", new CategoryUsage("마스크/팩세트", "일주일에 한두번 정도 필요에 따라 세면후에 얼굴에 골고루 바른뒤 15분정도 지난후에 화장솜으로 말끔히 닦아내거나 따듯한 물로 씻어줍니다."));
            put("6", new CategoryUsage("베이스메이크업", ""));
            put("7", new CategoryUsage("클렌징워터", "화장솜에 흐르지 않을 정도로 클렌징 워터를 적셔준뒤에 피부결 방향대로 안쪽에서 바깥쪽으로 쓸어서 지워주시면 됩니다."));
            
            put("8", new CategoryUsage("헤어팩", "물기가 있는 상태에서 헤어팩을 바른후 3분정도 경과한뒤에 말끔하게 씻어줍니다."));
            put("9", new CategoryUsage("샴푸", "적당량을 덜어서 두피와 모발에 골고루 발라 마사지 하듯 부드럽게 거품을 내어 깨끗하게 헹궈주세요."));
            
            put("10", new CategoryUsage("페이스오일", ""));
            
            //데이, 나이트, 수분크림
            put("11", new CategoryUsage("잘베크림", "소량으로 필요부위에 넓게 펴바르면 됩니다."));
            put("12", new CategoryUsage("아이케어", "매일 아침, 저녁으로 피부결을 정돈후에 주름이 있는 눈가에 적당량을 덜어 부드럽게 펴 발라줍니다. 눈가외에 입주위, 미간 등과 같이 주름이 생기기 쉬운 부위에도 적당량을 발라주면 좋습니다."));
            
            put("13", new CategoryUsage("스킨/토너", "클렌징 후 화장솜을 사용하여 얼굴과 목 부분에 부드럽게 닦아내듯이 발라줍니다. 매일 아침 저녁 필요시 사용합니다."));
            
            put("14", new CategoryUsage("풋케어", "아침, 저녁으로 발 전체에 마사지 하듯이 골고루 발라줍니다. 한번에 소량만 덜어 사용하셔도 됩니다."));
            
        }
    };
    
    //SmartStore
    public static final HashMap<String, CategoryUsage> categoryUsage = new HashMap<>() {
        {
            
            put("50000458", new CategoryUsage("클렌징 비누", "물기있는 손에 적당량을 덜어 마사지하듯 문지른 후 미온수로 헹구어 내세요."));
            put("50000291", new CategoryUsage("입욕제", ""));
            //샤월젤
            put("50000285", new CategoryUsage("바디클렌져", "적당량을 바디스폰지나 바디솔, 타월에 묻혀 거품을 낸 후 전신에 마사지 하듯이 문질러주세요. 노폐물이 제거되면 물로 깨끗히 씻어주세요."));
            put("50000408", new CategoryUsage("바디로션", "샤워후 물기가 약간 남아있을때 바디로션 적당량을 손에 덜어 필요한 부위에 고르게 발라주면 좋습니다."));
            put("50000469", new CategoryUsage("마스크/팩세트", "일주일에 한두번 정도 필요에 따라 세면후에 얼굴에 골고루 바른뒤 15분정도 지난후에 화장솜으로 말끔히 닦아내거나 따듯한 물로 씻어줍니다."));
            put("50000470", new CategoryUsage("베이스메이크업", ""));
            put("50000456", new CategoryUsage("클렌징워터", "화장솜에 흐르지 않을 정도로 클렌징 워터를 적셔준뒤에 피부결 방향대로 안쪽에서 바깥쪽으로 쓸어서 지워주시면 됩니다."));
            
            put("50000301", new CategoryUsage("헤어팩", "물기가 있는 상태에서 헤어팩을 바른후 3분정도 경과한뒤에 말끔하게 씻어줍니다."));
            put("50000297", new CategoryUsage("샴푸", "적당량을 덜어서 두피와 모발에 골고루 발라 마사지 하듯 부드럽게 거품을 내어 깨끗하게 헹궈주세요."));
            
            put("50000443", new CategoryUsage("페이스오일", ""));
            
            //데이, 나이트, 수분크림
            put("50000440", new CategoryUsage("크림", ""));
            put("50000441", new CategoryUsage("아이케어", "매일 아침, 저녁으로 피부결을 정돈후에 주름이 있는 눈가에 적당량을 덜어 부드럽게 펴 발라줍니다. 눈가외에 입주위, 미간 등과 같이 주름이 생기기 쉬운 부위에도 적당량을 발라주면 좋습니다."));
            
            put("50000437", new CategoryUsage("스킨/토너", "클렌징 후 화장솜을 사용하여 얼굴과 목 부분에 부드럽게 닦아내듯이 발라줍니다. 매일 아침 저녁 필요시 사용합니다."));
            
            put("50000290", new CategoryUsage("풋케어", "아침, 저녁으로 발 전체에 마사지 하듯이 골고루 발라줍니다. 한번에 소량만 덜어 사용하셔도 됩니다."));
            
        }
    };
    
    //Coopang
    public static final HashMap<String, CategoryUsage> categoryUsageCoopang = new HashMap<>() {
        {
            put("56163", new CategoryUsage("샴푸", ""));
            put("56288", new CategoryUsage("기능성샴푸", ""));
            
            put("56174", new CategoryUsage("일반오일", ""));

            put("56163", new CategoryUsage("데이크림", ""));
            put("56166", new CategoryUsage("나이트크림", ""));
            
            put("56127", new CategoryUsage("클렌징비누", ""));
            put("56140", new CategoryUsage("클렌지워터", ""));
            put("56185", new CategoryUsage("워시오프 마스크팩", ""));
            
            put("56213", new CategoryUsage("바디워시", ""));
            put("56222", new CategoryUsage("바디로션", ""));
            
            put("56241", new CategoryUsage("풋케어", ""));
        }
    };
    
    //Coopang catgory id
    public static final HashMap<String, Integer> categoryCodeCoopang = new HashMap<>() {
        {
            put("", 0);
            put("나이트크림", 56166);
            
        }
    };
    
    public static final HashMap<String, String> brandOverview = new HashMap<>() {
        {
            put("Sante", "https://moondrive81.cafe24.com/GKoo/Cosmetic_overview/SanteOverwiew.png");
            put("physiogel", "https://moondrive81.cafe24.com/GKoo/Cosmetic_overview/physiogel.png");
            put("lavera", "https://moondrive81.cafe24.com/GKoo/Cosmetic_overview/lavera.png");
            
        }
    };
    
    public enum Gender { MALE, FEMALE, KIDS }
}

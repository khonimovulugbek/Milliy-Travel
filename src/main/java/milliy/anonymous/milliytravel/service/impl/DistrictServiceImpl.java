package milliy.anonymous.milliytravel.service.impl;


import lombok.RequiredArgsConstructor;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.DistrictDTO;
import milliy.anonymous.milliytravel.entity.DistrictEntity;
import milliy.anonymous.milliytravel.enums.Region;
import milliy.anonymous.milliytravel.repository.DistrictRepository;
import milliy.anonymous.milliytravel.service.DistrictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository repository;
    public DistrictDTO create(Integer id, DistrictDTO dto){
        Optional<DistrictEntity> byId = repository.findById(id);
        if (byId.isEmpty()){ DistrictEntity entity = new DistrictEntity();
            entity.setName(dto.getName());
            entity.setRegion(dto.getRegion());
            repository.save(entity);

        }
        return dto;
    }
    public List<String> getByRegion(Region region){

        List<DistrictEntity> all = repository.findAll();
        if (all.isEmpty()){
            List<DistrictEntity> init = init();
            repository.saveAll(init);
        }

        List<String> districtsList = new ArrayList<>();
        repository.findAllByRegion(region).forEach(districtEntity -> districtsList.add(districtEntity.getName()));
        return districtsList;
    }
    public DistrictDTO toDto(DistrictEntity entity){
        DistrictDTO dto = new DistrictDTO();
        dto.setName(entity.getName());
        dto.setRegion(entity.getRegion());
        return dto;
    }
    public List<DistrictEntity> init (){

        List<DistrictEntity> entities= new LinkedList<>();
        entities.add(new DistrictEntity("Amudaryo", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Beruniy", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Kegeyli", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Qanlikol", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Qoraozak", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Qongirot", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Moynoq", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Nukus", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Taxiatosh", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Taxtakopir", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Tortkol", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Xojayli", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Chimboy", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Shomanoy", Region.RESPUBLIC_OF_KARAKALPAKISTAN));
        entities.add(new DistrictEntity("Ellikqala", Region.RESPUBLIC_OF_KARAKALPAKISTAN));

        entities.add(new DistrictEntity("Andijon", Region.ANDIJAN));
        entities.add(new DistrictEntity("Xonabod", Region.ANDIJAN));
        entities.add(new DistrictEntity("Andijon", Region.ANDIJAN));
        entities.add(new DistrictEntity("Asaka", Region.ANDIJAN));
        entities.add(new DistrictEntity("Baliqchi", Region.ANDIJAN));
        entities.add(new DistrictEntity("Boz", Region.ANDIJAN));
        entities.add(new DistrictEntity("Buloqboshi", Region.ANDIJAN));
        entities.add(new DistrictEntity("Jalaquduq", Region.ANDIJAN));
        entities.add(new DistrictEntity("Izboskan", Region.ANDIJAN));
        entities.add(new DistrictEntity("Qorgontepa", Region.ANDIJAN));
        entities.add(new DistrictEntity("Marhamat", Region.ANDIJAN));
        entities.add(new DistrictEntity("Oltinkol", Region.ANDIJAN));
        entities.add(new DistrictEntity("Paxtaobod", Region.ANDIJAN));
        entities.add(new DistrictEntity("Ulugnor", Region.ANDIJAN));
        entities.add(new DistrictEntity("Xojaobod", Region.ANDIJAN));
        entities.add(new DistrictEntity("Shahrixon", Region.ANDIJAN));

        entities.add(new DistrictEntity("Buxoro", Region.BUKHARA));
        entities.add(new DistrictEntity("Kogon", Region.BUKHARA));
        entities.add(new DistrictEntity("Buxoro", Region.BUKHARA));
        entities.add(new DistrictEntity("Vobkent", Region.BUKHARA));
        entities.add(new DistrictEntity("Jondor", Region.BUKHARA));
        entities.add(new DistrictEntity("Kogon", Region.BUKHARA));
        entities.add(new DistrictEntity("Olot", Region.BUKHARA));
        entities.add(new DistrictEntity("Peshku", Region.BUKHARA));
        entities.add(new DistrictEntity("Romitan", Region.BUKHARA));
        entities.add(new DistrictEntity("Shofirkon", Region.BUKHARA));
        entities.add(new DistrictEntity("Qorovulbozor", Region.BUKHARA));
        entities.add(new DistrictEntity("Qorakol", Region.BUKHARA));
        entities.add(new DistrictEntity("Gijduvon", Region.BUKHARA));

        entities.add(new DistrictEntity("Jizzax", Region.JIZZAKH));
        entities.add(new DistrictEntity("Arnasoy", Region.JIZZAKH));
        entities.add(new DistrictEntity("Baxmal", Region.JIZZAKH));
        entities.add(new DistrictEntity("Dostlik", Region.JIZZAKH));
        entities.add(new DistrictEntity("Zarbdor", Region.JIZZAKH));
        entities.add(new DistrictEntity("Zafarobod", Region.JIZZAKH));
        entities.add(new DistrictEntity("Zomin", Region.JIZZAKH));
        entities.add(new DistrictEntity("Mirzachol", Region.JIZZAKH));
        entities.add(new DistrictEntity("Paxtakor", Region.JIZZAKH));
        entities.add(new DistrictEntity("Forish", Region.JIZZAKH));
        entities.add(new DistrictEntity("Sharof", Region.JIZZAKH));
        entities.add(new DistrictEntity("Gallaorol", Region.JIZZAKH));
        entities.add(new DistrictEntity("Yangiobod", Region.JIZZAKH));


        entities.add(new DistrictEntity("Qarshi", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Shahrisabz", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Dehqonobod", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Kasbi", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Kitob", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Koson", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Mirishkor", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Muborak", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Nishon", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Chiroqchi", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Shahrisabz", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Yakkabog", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Qamashi", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Qarshi", Region.KASHKADARYA));
        entities.add(new DistrictEntity("Guzor", Region.KASHKADARYA));

        entities.add(new DistrictEntity("Navoiy", Region.NAVOIY));
        entities.add(new DistrictEntity("Zarafshon", Region.NAVOIY));
        entities.add(new DistrictEntity("Karmana", Region.NAVOIY));
        entities.add(new DistrictEntity("Konimex", Region.NAVOIY));
        entities.add(new DistrictEntity("Navbahor", Region.NAVOIY));
        entities.add(new DistrictEntity("Nurota", Region.NAVOIY));
        entities.add(new DistrictEntity("Tomdi", Region.NAVOIY));
        entities.add(new DistrictEntity("Uchquduq", Region.NAVOIY));
        entities.add(new DistrictEntity("Xatirchi", Region.NAVOIY));
        entities.add(new DistrictEntity("Qiziltepa", Region.NAVOIY));

        entities.add(new DistrictEntity("Namangan", Region.NAMANGAN));
        entities.add(new DistrictEntity("Kosonsoy", Region.NAMANGAN));
        entities.add(new DistrictEntity("Mingbuloq", Region.NAMANGAN));
        entities.add(new DistrictEntity("Namangan", Region.NAMANGAN));
        entities.add(new DistrictEntity("Norin", Region.NAMANGAN));
        entities.add(new DistrictEntity("Pop", Region.NAMANGAN));
        entities.add(new DistrictEntity("Toraqorgon", Region.NAMANGAN));
        entities.add(new DistrictEntity("Uychi", Region.NAMANGAN));
        entities.add(new DistrictEntity("Uchqorgon", Region.NAMANGAN));
        entities.add(new DistrictEntity("Chortoq", Region.NAMANGAN));
        entities.add(new DistrictEntity("Chust", Region.NAMANGAN));
        entities.add(new DistrictEntity("Yangiqorgon", Region.NAMANGAN));

        entities.add(new DistrictEntity("Samarqand", Region.SAMARKAND));
        entities.add(new DistrictEntity("Kattaqorgon", Region.SAMARKAND));
        entities.add(new DistrictEntity("Bulungur", Region.SAMARKAND));
        entities.add(new DistrictEntity("Jomboy", Region.SAMARKAND));
        entities.add(new DistrictEntity("Ishtixon", Region.SAMARKAND));
        entities.add(new DistrictEntity("Kattaqorgon", Region.SAMARKAND));
        entities.add(new DistrictEntity("Narpay", Region.SAMARKAND));
        entities.add(new DistrictEntity("Nurobod", Region.SAMARKAND));
        entities.add(new DistrictEntity("Oqdaryo", Region.SAMARKAND));
        entities.add(new DistrictEntity("Payariq", Region.SAMARKAND));
        entities.add(new DistrictEntity("Pastdargom", Region.SAMARKAND));
        entities.add(new DistrictEntity("Paxtachi", Region.SAMARKAND));
        entities.add(new DistrictEntity("Samarqand", Region.SAMARKAND));
        entities.add(new DistrictEntity("Toyloq", Region.SAMARKAND));
        entities.add(new DistrictEntity("Urgut", Region.SAMARKAND));
        entities.add(new DistrictEntity("Qoshrabot", Region.SAMARKAND));

        entities.add(new DistrictEntity("Termiz", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Angor", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Boysun", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Denov", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Jarqorgon", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Muzrobod", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Oltinsoy", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Sariosiyo", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Termiz", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Uzun", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Sherobod", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Shorchi", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Qiziriq", Region.SURKHANDARYA));
        entities.add(new DistrictEntity("Qumqorgon", Region.SURKHANDARYA));

        entities.add(new DistrictEntity("Guliston", Region.SIRDARYA));
        entities.add(new DistrictEntity("Yangiyer", Region.SIRDARYA));
        entities.add(new DistrictEntity("Shirin", Region.SIRDARYA));
        entities.add(new DistrictEntity("Boyovut", Region.SIRDARYA));
        entities.add(new DistrictEntity("Guliston", Region.SIRDARYA));
        entities.add(new DistrictEntity("Mirzaobod", Region.SIRDARYA));
        entities.add(new DistrictEntity("Oqoltin", Region.SIRDARYA));
        entities.add(new DistrictEntity("Sardoba", Region.SIRDARYA));
        entities.add(new DistrictEntity("Sayxunobod", Region.SIRDARYA));
        entities.add(new DistrictEntity("Sirdaryo", Region.SIRDARYA));
        entities.add(new DistrictEntity("Xovos", Region.SIRDARYA));

        entities.add(new DistrictEntity("Nurafshon", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Angren", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Bekobod", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Olmaliq", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Ohangaron", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Chirchiq", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Yangiyol", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Bekobod", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Boka", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Bostonliq", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Zangiota", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Qibray", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Quyichirchiq", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Oqqorgon", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Ohangaron", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Parkent", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Piskent", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Toshkent", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Ortachirchiq", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Chinoz", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Yuqorichirchiq", Region.TASHKENT_REGION));
        entities.add(new DistrictEntity("Yangiyol", Region.TASHKENT_REGION));

        entities.add(new DistrictEntity("Fargona", Region.FERGANA));
        entities.add(new DistrictEntity("Margilon", Region.FERGANA));
        entities.add(new DistrictEntity("Quvasoy", Region.FERGANA));
        entities.add(new DistrictEntity("Qoqon", Region.FERGANA));
        entities.add(new DistrictEntity("Beshariq", Region.FERGANA));
        entities.add(new DistrictEntity("Bogdod", Region.FERGANA));
        entities.add(new DistrictEntity("Buvayda", Region.FERGANA));
        entities.add(new DistrictEntity("Dangara", Region.FERGANA));
        entities.add(new DistrictEntity("Yozyovon", Region.FERGANA));
        entities.add(new DistrictEntity("Quva", Region.FERGANA));
        entities.add(new DistrictEntity("Qoshtepa", Region.FERGANA));
        entities.add(new DistrictEntity("Oltiariq", Region.FERGANA));
        entities.add(new DistrictEntity("Rishton", Region.FERGANA));
        entities.add(new DistrictEntity("Sox", Region.FERGANA));
        entities.add(new DistrictEntity("Toshloq", Region.FERGANA));
        entities.add(new DistrictEntity("Ozbekiston", Region.FERGANA));
        entities.add(new DistrictEntity("Uchkoprik", Region.FERGANA));
        entities.add(new DistrictEntity("Fargona", Region.FERGANA));
        entities.add(new DistrictEntity("Furqat", Region.FERGANA));

        entities.add(new DistrictEntity("Urganch", Region.KHOREZM));
        entities.add(new DistrictEntity("Xiva", Region.KHOREZM));
        entities.add(new DistrictEntity("Bogot", Region.KHOREZM));
        entities.add(new DistrictEntity("Gurlan", Region.KHOREZM));
        entities.add(new DistrictEntity("Urganch", Region.KHOREZM));
        entities.add(new DistrictEntity("Xiva", Region.KHOREZM));
        entities.add(new DistrictEntity("Xonqa", Region.KHOREZM));
        entities.add(new DistrictEntity("Hazorasp", Region.KHOREZM));
        entities.add(new DistrictEntity("Shovot", Region.KHOREZM));
        entities.add(new DistrictEntity("Yangiariq", Region.KHOREZM));
        entities.add(new DistrictEntity("Yangibozor", Region.KHOREZM));
        entities.add(new DistrictEntity("Qoshkopir", Region.KHOREZM));

        entities.add(new DistrictEntity("Bektemir", Region.TASHKENT));
        entities.add(new DistrictEntity("Mirzo Ulugbek", Region.TASHKENT));
        entities.add(new DistrictEntity("Mirobod", Region.TASHKENT));
        entities.add(new DistrictEntity("Olmazor", Region.TASHKENT));
        entities.add(new DistrictEntity("Sirgali", Region.TASHKENT));
        entities.add(new DistrictEntity("Uchtepa", Region.TASHKENT));
        entities.add(new DistrictEntity("Chilonzor", Region.TASHKENT));
        entities.add(new DistrictEntity("Shayxontohur", Region.TASHKENT));
        entities.add(new DistrictEntity("Yunusobod", Region.TASHKENT));
        entities.add(new DistrictEntity("Yakkasaroy", Region.TASHKENT));
        entities.add(new DistrictEntity("Yashnobod", Region.TASHKENT));

        return entities;
    }

    public List<DistrictDTO> getList(){

        List<DistrictDTO> dtos = new LinkedList<>();

        List<DistrictEntity> all = repository.findAll();

        if (all.size()==0){
            List<DistrictEntity> init = init();
            repository.saveAll(init);
            init.forEach(entity -> dtos.add(toDto(entity)));
        }

        all.forEach(entity -> dtos.add(toDto(entity)));

        return dtos;
    }
}

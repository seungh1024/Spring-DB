package com.seungh1024.repository.jdbctemplate;

import com.seungh1024.domain.Item;
import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.ItemSearchCond;
import com.seungh1024.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * SimpleJdbcInsert 사용
 *
 */
@Slf4j
public class JdbcTemplateItemRepositoryV3 implements ItemRepository {

//    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateItemRepositoryV3(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
//                .usingColumns("item_name","price","quantity"); //생략 가능
    }
    @Override
    public Item save(Item item) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(item);
        Number key = jdbcInsert.executeAndReturnKey(param);
        item.setId(key.longValue());
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item " +
                "set item_name = :itemName , price = :price,  quantity=:quantity " +
                "where id = :id";

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);
        template.update(sql,param);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = :id";
        try{
            Map<String, Object> param = Map.of("id", id);
            Item item = template.queryForObject(sql, param,itemRowMapper());
            return Optional.of(item);
        }catch(EmptyResultDataAccessException e){ // Optional이 null이면 예외가 발생함
            return Optional.empty();
        }
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class); //resultSet으로 각 이름에 맞게 매핑해줌 언더바에서 카멜 변수로 자동변환됨
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select id, item_name , price, quantity from item";
        //동적 쿼리
        if(StringUtils.hasText(itemName) || maxPrice != null){
            sql+=" where";
        }

        boolean andFlag = false;
        if(StringUtils.hasText(itemName)){
            sql += " item_name like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if(maxPrice != null){
            if(andFlag){
                sql += " and";
            }
            sql += " price <= :maxPrice";
        }
        log.info("sql={}", sql);

        return template.query(sql, param, itemRowMapper());
    }
}

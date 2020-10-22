package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExMapper extends BaseMapper {

    @Select("select COLUMN_NAME from information_schema.COLUMNS where table_name = #{table} and table_schema = #{database}")
    List<String> selectColumnNameByDatabaseAndTable(@Param("database") String database, @Param("table") String table);
}

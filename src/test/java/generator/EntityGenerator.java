package generator;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;


/**
 * 实体生成
 *
 * @author fengshuonan
 * @date 2017-08-23 12:15
 */
public class EntityGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:\\javaweb\\ut\\src\\main\\java");
        gc.setAuthor("wenjie");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setFileOverride(true);// 是否覆盖
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(false);
        gc.setActiveRecord(true); // 实体可操作数据库
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.129.133:3306/ut?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity("run.ut.app.model.domain");
        pc.setMapper("run.ut.app.mapper");
        pc.setXml("run.ut.app.mapper");
        pc.setService("run.ut.app.service");
        pc.setServiceImpl("run.ut.app.service.impl");
//        pc.setController(null);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        mpg.setCfg(cfg);

        String[] tables = {"teams_members"};
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tables);
        strategy.setControllerMappingHyphenStyle(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");// 表前缀
        mpg.setStrategy(strategy);
        mpg.execute();
    }

    @Test
    public void test(){
        String[] ids = new String[]{"1","2","3"};
        String json = JSON.toJSONString(ids);
        System.out.println(json);
    }
}

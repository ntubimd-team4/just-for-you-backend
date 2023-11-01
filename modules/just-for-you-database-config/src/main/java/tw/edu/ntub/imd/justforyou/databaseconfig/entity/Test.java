package tw.edu.ntub.imd.justforyou.databaseconfig.entity;


import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.SummaryRecordListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data
@Entity
@EntityListeners(SummaryRecordListener.class)
@Table(name = "summary_record", schema = Config.DATABASE_NAME)
public class Test {
}

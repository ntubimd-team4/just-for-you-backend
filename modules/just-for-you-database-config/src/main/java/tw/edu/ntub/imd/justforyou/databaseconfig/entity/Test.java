package tw.edu.ntub.imd.justforyou.databaseconfig.entity;


import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.SummaryRecordListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(SummaryRecordListener.class)
@Table(name = "summary_record", schema = Config.DATABASE_NAME)
public class Test {
    /**
     * 流水號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id", nullable = false, unique = true)
    private Integer sid;
}
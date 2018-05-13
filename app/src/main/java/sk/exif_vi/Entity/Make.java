package sk.exif_vi.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Make {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "MAKE_NAME")
    private String name;

    @Generated(hash = 239494108)
    public Make(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 187806091)
    public Make() {
    }

    public Make(String make) {
        this.name = make;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

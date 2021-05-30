package com.mimehoo.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test") //  实体类和表对应
public class Test {
    @TableId(type = IdType.AUTO) // 主键对应，主键自增原则
    @TableField("id") // 属性和字段对应
    private Integer id;

    // @TableField("content") 字段名和属性名相同或符合驼峰转换规则，可省略@TableField注解
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}

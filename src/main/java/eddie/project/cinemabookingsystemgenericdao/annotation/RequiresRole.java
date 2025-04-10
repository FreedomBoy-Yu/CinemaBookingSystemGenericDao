package eddie.project.cinemabookingsystemgenericdao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 權限控制註解，用於標記需要特定角色才能訪問的方法
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {
    /**
     * 允許訪問的角色列表
     * 0: 最高權限管理員
     * 1: 一般使用者
     * 2: VIP
     * 3: 管理員
     */
    int[] value();
    
    /**
     * 邏輯關係：ANY表示滿足任一角色即可，ALL表示必須滿足所有角色
     */
    Logical logical() default Logical.ANY;
    
    enum Logical {
        /**
         * 必須滿足所有指定角色
         */
        ALL,
        
        /**
         * 滿足任一指定角色即可
         */
        ANY
    }
}

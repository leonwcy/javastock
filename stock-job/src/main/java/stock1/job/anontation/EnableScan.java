package stock1.job.anontation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;



@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ScanConfig.class})
public @interface EnableScan {
}

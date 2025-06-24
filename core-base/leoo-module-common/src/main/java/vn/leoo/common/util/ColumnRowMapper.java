package vn.leoo.common.util;

import org.springframework.jdbc.core.BeanPropertyRowMapper;


public class ColumnRowMapper<T> extends BeanPropertyRowMapper<T> {

  

    private ColumnRowMapper(final Class<T> mappedClass){
        super(mappedClass);
    }

//    @SneakyThrows
//    protected String underscoreName(final String name){
//        final Column annotation;
//        String columnName = null;
//        Field declaredField = null;
//
//        try {
//                declaredField = getMappedClass().getDeclaredField(name);
//        } catch (NoSuchFieldException | SecurityException e){
//            if(getMappedClass().getSuperclass() != null){
//                declaredField = getMappedClass().getSuperclass().getDeclaredField(name);
//            } else {
//                LOGGER.error(String.format(" --- ERROR Map Column : %s",e.getMessage()));
//            }
//        }
//        if(declaredField == null || (annotation = declaredField.getAnnotation(Column.class)) == null || StringUtils.isEmpty(columnName = annotation.name())){
//            super.underscoreName(name);
//        }
//        return StringUtils.lowerCase(columnName);
//    }

    public static <T> BeanPropertyRowMapper<T> newInstance(final Class<T> mappedClass){
        return new ColumnRowMapper<>(mappedClass);
    }
}

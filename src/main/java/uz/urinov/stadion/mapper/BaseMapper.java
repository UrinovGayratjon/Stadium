package uz.urinov.stadion.mapper;

public interface BaseMapper<A, B> {
    B mapTo(A a);
    A mapFrom(B b);

}

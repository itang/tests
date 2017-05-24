package test;


public class Jeep implements Car {

    //具体类抛出的异常要不比基类的少（收窄）， 要不是基类的跑出异常的子类型， 也不能是基类未定义的异常类型体系， 运行时类型除外

    @Override
    public void drive(int people) throws DriveException, SubDriveException, MyRuntimeException /*UnknownException */ {
        throw new SubDriveException();
        //throw new UnknownException(); // Unhandled Exception
    }
}

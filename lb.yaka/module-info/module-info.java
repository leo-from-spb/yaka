/**
 * YAKA — Yet Another Kotlin Assertions framework.
 **/
open module lb.yaka {

    requires transitive kotlin.stdlib;
    requires static     kotlin.stdlib.jdk8;

    exports lb.yaka;
    exports lb.yaka.gears;
    exports lb.yaka.expectations;
    exports lb.yaka.utils;

}
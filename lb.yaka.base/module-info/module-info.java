/**
 * YAKA — Yet Another Kotlin Assertions framework.
 **/
open module lb.yaka.base {

    requires transitive kotlin.stdlib;
    requires static     kotlin.stdlib.jdk8;

    exports lb.yaka.base;
    exports lb.yaka.base.gears;
    exports lb.yaka.base.expectations;
    exports lb.yaka.base.utils;

}
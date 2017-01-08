#Fork of [Xstream Migration Tool](http://wiki.pmease.com/display/xmt/Documentation+Home)

Changes:

1. Added annotation [`@NeedMigration`](https://github.com/astukalo/xmt/blob/master/src/main/java/xyz/a5s7/xmt/NeedMigration.java). You need to mark a class with this annotation in order to activate migration.
2. When you save objects with `toXML()`, even nested element will contain a version, if it is annotated:

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <xyz.a5s7.xmt.bean.BeanWithMigration>
       <str>test</str>
       <dbl>1231.6</dbl>
       <!--this field was removed from java-->
       <i>5</i>
       <nestedBean version="1">
           <i>35</i>
           <str>nnnnn</str>
       </nestedBean>
   </xyz.a5s7.xmt.bean.BeanWithMigration>
   ```
   (In this example you can see that for the parent class `version` attribute can be omitted, it is 0 by default.)
3. `private void migrate1(VersionedDocument dom, Stack<Integer> versions)` changed to
`private void migrate1(DefaultElement dom, Deque<Integer> versions)`
4. Added [`IgnoreUnknownElementsMapConverter`](https://github.com/astukalo/xmt/blob/master/src/main/java/xyz/a5s7/xmt/IgnoreUnknownElementsMapConverter.java), which ignores unknown classes in map (i.e. removed in the new versions).
The same IgnoreUnknownElementsCollectionConverter may be required as well, but I haven't added it yet.

The best way to check how it works is to take a look at unit test: [XMTTest](https://github.com/astukalo/xmt/blob/master/src/test/java/xyz/a5s7/xmt/XMTTest.java).

Feel free to use it. Pull requests, bugs/issues reporting are also welcome.

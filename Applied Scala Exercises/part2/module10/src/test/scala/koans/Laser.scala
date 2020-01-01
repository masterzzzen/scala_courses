
/* Copyright (C) 2010-2018 Escalate Software, LLC. All rights reserved. */

package lab {
  package awesome {
    package laser {
      class Gun(watts: Int) {
        def shoot() = {
          new Beam(watts * 10 )
        }
        class Beam (val lumens: Int)
      }
    }
  }
}

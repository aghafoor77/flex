let endpoints =  
  {
    AM:'traceapictrl', // account management
    //'AM':'http://localhost:9000', // account management
    AR:'animalctrl',
    ADR:'animalctrl',
    BD:'animalctrl',
    FP:'feedctrl',
    FT:'feedctrl', // Check with Breed
    TM:'feedctrl',
    AE:'healthctrl',
    GHE:'healthctrl',
    GHO:'healthctrl',    
    DT:'healthctrl',
    OS:'prebirthctrl',
    MB:'prebirthctrl',
    SU:'prebirthctrl',    
    BD:'generaldatactrl',
    RS:'reportslaughterhousectrl',
    HR:'resourcesctrl', // Farm, Farmer, etc
    //CHR:'http://192.168.10.127:9090'
    CHR:'http://localhost:9090',
    SRV:'http://localhost'
    
  }

  export default endpoints;

/*
 * let endpoints =  
  {
    AM:'traceapictrl', // account management
    //'AM':'http://localhost:9000', // account management
    AR:'animalregctrl',
    OS:'ordersemenctrl',
    MB:'movebullforherdctrl',
    SU:'semenutilizationctrl',
    AE:'animalexaminationctrl',
    BD:'generaldatactrl',
    GHE:'generalhealthexaminationctrl',
    GHO:'generalhealthexaminationctrl',
    FP:'feedpatternctrl',
    DT:'drugstreatmentsctrl',
    TM:'temporarymovementctrl',
    ADR:'animalderegisterctrl',
    RS:'reportslaughterhousectrl',
    HR:'resourcesctrl',
    CHR:'http://10.112.0.39:9090'
  }

  export default endpoints;*/

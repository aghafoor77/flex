import React from 'react';

const Charts = React.lazy(() => import('./views/charts/Charts'));
const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));

const FarmerRegistrar = React.lazy(() => import('./views/farm/FarmerRegistrar'));
const Facilities = React.lazy(() => import('./views/farm/Facilities'));
const FarmData = React.lazy(() => import('./views/farm/FarmData'));


const HumanResource = React.lazy(() => import('./views/farmer/HumanResource'));
const HumanResourceDetail = React.lazy(() => import('./views/farmer/HumanResourceDetail'));
const HumanResourceEdit = React.lazy(() => import('./views/farmer/HumanResourceEdit'));


const OrderSemenForm = React.lazy(() => import('./views/insemination/OrderSemenForm'));
const OrderSemenList = React.lazy(() => import('./views/insemination/OrderSemenList'));
const OrderSemenDetail = React.lazy(() => import('./views/insemination/OrderSemenDetail'));
const Insemination = React.lazy(() => import('./views/insemination/Insemination'));
const BullMovementForHerd = React.lazy(() => import('./views/insemination/BullMovementForHerd'));
const BullMovementList = React.lazy(() => import('./views/insemination/BullMovementList'));
const BullMovementDetail = React.lazy(() => import('./views/insemination/BullMovementDetail'));
const Examination = React.lazy(() => import('./views/insemination/Examination'));
const PregnancyWithStatusList = React.lazy(() => import('./views/insemination/PregnancyWithStatusList'));
const PregRecordDetail = React.lazy(() => import('./views/insemination/PregRecordDetail'));
const RegisterAnimal = React.lazy(() => import('./views/animals/RegisterAnimal'));
const GeneralAnimalHealth = React.lazy(() => import('./views/animals/GeneralAnimalHealth'));
const GeneralAnimalHealthDetail = React.lazy(() => import('./views/animals/GeneralAnimalHealthDetail'));
const GeneralAnimalHealthEdit = React.lazy(() => import('./views/animals/GeneralAnimalHealthEdit'));

const ObserveAnimal = React.lazy(() => import('./views/animals/ObserveAnimal'));
const ObserveAnimalEdit = React.lazy(() => import('./views/animals/ObserveAnimalEdit'));

const FeedingPattern = React.lazy(() => import('./views/animals/FeedingPattern'));
const AssignFeed2Animal = React.lazy(() => import('./views/animals/AssignFeed2Animal'));
const AssignFeedDetail = React.lazy(() => import('./views/animals/AssignFeedDetail'));
const RegisteredAnimalsList = React.lazy(() => import('./views/animals/RegisteredAnimalsList'));
const RegisteredAnimalsDetail = React.lazy(() => import('./views/animals/RegisteredAnimalsDetail'));
const RegisterAnimalEdit = React.lazy(() => import('./views/animals/RegisterAnimalEdit'));

const AnimalTreatment = React.lazy(() => import('./views/drugs/AnimalTreatment'));
const AnimalTreatmentDetail = React.lazy(() => import('./views/drugs/AnimalTreatmentDetail'));
const AnimalTreatmentEdit = React.lazy(() => import('./views/drugs/AnimalTreatmentEdit'));

const TemporaryMovementDetail = React.lazy(() => import('./views/movements/TemporaryMovementDetail'));
const TemporaryMovement = React.lazy(() => import('./views/movements/TemporaryMovement'));
const TemporaryMovementGroup = React.lazy(() => import('./views/movements/TemporaryMovementGroup'));
const TemporaryMovementEdit = React.lazy(() => import('./views/movements/TemporaryMovementEdit'));
const AnimalDeregister = React.lazy(() => import('./views/movements/AnimalDeregister'));
const AnimalDeregisterEdit = React.lazy(() => import('./views/movements/AnimalDeregisterEdit'));


const SaleReport2SlaughterhouseList = React.lazy(() => import('./views/sell/SaleReport2SlaughterhouseList'));
const SaleReport2Slaughterhouse = React.lazy(() => import('./views/sell/SaleReport2Slaughterhouse'));
const SaleReport2SlaughterhouseResponse = React.lazy(() => import('./views/sell/SaleReport2SlaughterhouseResponse'));



const GenericView = React.lazy(() => import('./views/genericview/GenericView'));
const AnimalResourceGraph = React.lazy(() => import('./views/genericview/AnimalResourceGraph'));
const GenericGraphView = React.lazy(() => import('./views/genericview/GenericGraphView'));

const WalletHandling = React.lazy(() => import('./views/wallet/WalletHandling'));
const EditProfile = React.lazy(() => import('./views/farmer/EditProfile'));

const ServicesStatusHandling = React.lazy(() => import('./views/wallet/ServicesStatusHandling'));


const Test = React.lazy(() => import('./views/identity/Test'));




//const SellEdit = React.lazy(() => import('./views/sell/SellEdit'));





//const TestAnimal = React.lazy(() => import('./views/animals/TestAnimal'));
const RegisterIdentity = React.lazy(() => import('./views/identity/RegisterIdentity'));






const routes = [

  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },

  { path: '/farm', name: 'Farm', component: Facilities, exact: true },
  { path: '/farm/farmer', name: 'Registration1', component: FarmerRegistrar },
  { path: '/farm/faciities', name: 'Facilities', component: Facilities},
  { path: '/farm/farm', name: 'Farm', component: FarmData},
  
  { path: '/farmer', name: 'Human Resource', component: HumanResource, exact: true },
  { path: '/farmer/farmer', name: 'Human Resource', component: HumanResource },
  { path: '/farmer/detail', name: 'Human Resource Info', component: HumanResourceDetail},
  { path: '/farmer/detail1/:resourceId', name: 'Human Resource Update', component: HumanResourceEdit},
  
  { path: '/insemination', name: 'Insemination', component: OrderSemenList, exact: true },
  { path: '/insemination/order', name: 'Order Semen', component: OrderSemenForm },
  { path: '/insemination/orderlist/:osid', name: 'Order Details', component: OrderSemenDetail },
  { path: '/insemination/orderlist', name: 'Order Semen  List', component: OrderSemenList },


  { path: '/insemination/su', name: 'Insemination', component: Insemination },
  { path: '/insemination/mb4h', name: 'Move Bull For Herd ', component: BullMovementForHerd },
  { path: '/insemination/mb4hl/:mb4hid', name: 'Bull Movement Record Details', component: BullMovementDetail },
  { path: '/insemination/mb4hl',  name: 'List of Bull Movement', component: BullMovementList },


  { path: '/insemination/pregexam', name: 'Pregnancy Examination', component: Examination },
  { path: '/insemination/pregl/:peno',  name: 'Pregnancy Record Detail', component: PregRecordDetail },
  { path: '/insemination/pregl', name: 'Pregnancy Records', component: PregnancyWithStatusList },
  
  { path: '/animals', name: 'Animals', component: OrderSemenList, exact: true },
  { path: '/animal/reg', name: 'Animal Registration', component: RegisterAnimal },
  { path: '/animals/uar/:animalID', name: 'Update Animal Data', component: RegisterAnimalEdit },
  { path: '/animals/health', name: 'General Health Examination', component: GeneralAnimalHealth },
  
  { path: '/animals/healthrecords/gahe/:gaheid', name: 'General Health Examination Edit', component: GeneralAnimalHealthEdit },
  { path: '/animals/healthrecords/gaho/:gahoid', name: 'General Health Observation Edit', component: ObserveAnimalEdit },

  { path: '/animals/healthrecords', name: 'General Health Examination', component: GeneralAnimalHealthDetail },
  { path: '/animals/observe', name: 'Animal Observation', component: ObserveAnimal },

  { path: '/animals/feeding', name: 'Animal Feeding Pattern', component: FeedingPattern },
  { path: '/animals/animalfeeding/:fpid',  name: 'Feed detail and assignment', component: AssignFeedDetail },
  { path: '/animals/animalfeeding', name: 'Assign Feed to Animal', component: AssignFeed2Animal },
  
  { path: '/animal/reglist/:animalID', name: 'Animal detail', component: RegisteredAnimalsDetail },
  { path: '/animal/reglist', name: 'All Registered Animals', component: RegisteredAnimalsList },
  { path: '/animals/animaldt', name: 'Drugs and Treatments', component: AnimalTreatment },
  { path: '/animals/animaldtlist/:dtid', name: 'Drugs and Treatments Edit', component: AnimalTreatmentEdit },
  { path: '/animals/animaldtlist', name: 'Drugs and Treatments List', component: AnimalTreatmentDetail },
  
  { path: '/movements', name: 'Movements', component: TemporaryMovementDetail, exact: true }, 
  { path: '/movements/out', name: 'Move out', component: TemporaryMovement },
  { path: '/movements/in/:tmid', name: 'came back', component: TemporaryMovementGroup },
  { path: '/movements/edit/:tmid', name: 'came back', component: TemporaryMovementEdit },
  { path: '/movements/list', name: 'Movements List', component: TemporaryMovementDetail },

  { path: '/movements/request2slaughterhus',  name: 'Requet to Slaughterhouse', component: SaleReport2Slaughterhouse },
  { path: '/movements/r2shuslist/:rid', name: 'Sale Orders', component: SaleReport2SlaughterhouseResponse },
  { path: '/movements/r2shuslist', name: 'Sale Orders', component: SaleReport2SlaughterhouseList },
  { path: '/movements/deregister/:animalID', name: 'Deregister', component: AnimalDeregisterEdit }, 
  { path: '/movements/deregister', name: 'Deregister', component: AnimalDeregister }, 
  { path: '/movements/gv', name: 'GenericView', component: GenericView }, 
  { path: '/movements/arg', name: 'AnimalResourceGraph', component: AnimalResourceGraph }, 
  { path: '/movements/ggv', name: 'GenericGraphView', component: GenericGraphView }, 
  
  
  { path: '/identity/test', name: 'Test', component: Test }, 

  { path: '/identity/register', name: 'Identity Registration', component: RegisterIdentity },
  { path: '/dt/wallet', name: 'WalletHandling', component: WalletHandling },
  { path: '/dt/status', name: 'ServicesStatus', component: ServicesStatusHandling },
  { path: '/dt/profile', name: 'Profile', component: EditProfile },
          
  //{ path: '/animals/test', name: 'Test Animal', component: TestAnimal },

  { path: '/charts', name: 'Charts', component: Charts }
];

export default routes;

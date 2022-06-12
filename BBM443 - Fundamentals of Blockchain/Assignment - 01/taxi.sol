pragma solidity 0.6.1;

contract taxi {
    uint256 public participantCount = 0;
    uint256 public companyBalance = 0;
    uint256 public ownedCarId;
    uint256 public time;
    uint256 public lastExpensesTime;
    uint256 public lastProfitShareTime;
    uint256 public fixedExpenses;
    uint256 public fixedTaxiPay;
    uint256 public participationFee;
    uint256 public sixMonthTime = 15778463;
    uint256 public oneMonthTime = 2629743;

    address payable public manager;
    address payable public carDealer;
    address[9] participantsAddress;
    
    car public proposedCar;
    car public proposedRepurchase;
    driverStruct public driver;
    
    enum state { Waiting, Approval, Fired }
    
    mapping(address => participant) public Participants;
    
    struct car {
        uint256 carId;
        uint256 offerValidTime;
        uint256 price;
        uint256 trueVoteCount;
        uint256 falseVoteCount;
        state approvalState;
    }
    
    struct driverStruct {
        address payable identity;
        uint256 salary;
        uint256 balance;
        uint256 lastPayDate;
        uint256 trueVoteCount;
        uint256 falseVoteCount;
        state approvalState;
    }
    
    struct participant {
        address payable identity;
        uint256 balance;
        uint256 isGiveVoteForDriver;
        uint256 isGiveVoteForCar;
    }
    
    modifier onlyManager() {
        require(msg.sender == manager);
        _;
    }
    
    modifier onlyCarDealer() {
        require(msg.sender == carDealer);
        _;
    }
    
    modifier onlyParticipants() {
        require(Participants[msg.sender].identity == msg.sender);
        _;
    }
    
    modifier execptParticipants() {
        require(Participants[msg.sender].identity != msg.sender);
        _;
    }
    
    modifier onlyDriver() {
        require(msg.sender == driver.identity);
        _;
    }
    
    constructor() public {
        manager = msg.sender;
        fixedExpenses = 10;
        participationFee = 50;
        fixedTaxiPay = 1;
    }
    
    function Join() public execptParticipants payable returns(string memory){
        if(participantCount < 9){
            if(Participants[msg.sender].identity == msg.sender){
                    return "Dear caller, you are already a participant in this investment.";
            }else{
                if(msg.value < participationFee)
                {
                    return "Sorry you cannot be participant because being participants cost is larger than your value that your send.";
                }else{
                    Participants[msg.sender] = participant(msg.sender,0,0,0);
                    companyBalance += participationFee;
                    participantsAddress[participantCount] = msg.sender;
                    participantCount += 1;
                    manager.transfer(msg.value);
                    return "Congratulations you have been participant in this taxi investment. Enjoy with your profit :)";
                }
            }
        }else{
            return "Sorry you cannot be participant because there is already 9 participants.";
        }
        
    }
    
    function PayTaxiCharge() public payable returns(string memory){
        manager.transfer(msg.value);
            companyBalance += fixedTaxiPay;
            return "Thank you for chosing us, fixed taxi pay has been payed.";
    }
    
    function SetCarDealer(address payable _carDealerAddress) public onlyManager returns(string memory){
        carDealer = _carDealerAddress;
        return "Car dealer has been setted by manager.";
    }
    
    function PurchaseCar() public onlyManager payable returns(string memory){
        if((proposedCar.falseVoteCount + proposedCar.trueVoteCount == participantCount) && (proposedCar.falseVoteCount > ((uint) (participantCount / 2))))
        {
            return "Voting has been end and offer rejected by participants";
        }else{
            if(proposedCar.trueVoteCount < ((uint)(participantCount / 2)))
            {
                return "There are not enough approve vote.";
            }else{
                if(proposedCar.offerValidTime == 0)
                {
                    return "There is no proposed car. Ask car dealer to arrange new proposel.";
                }else{
                    if(proposedCar.offerValidTime < block.timestamp)
                    {
                        return "Offers valid time has been passed. Ask car dealer to arrange new proposel.";
                    }else{
                        ownedCarId = proposedCar.carId;
                        companyBalance -= proposedCar.price;
                        carDealer.transfer(proposedCar.price);
                    }
                }
            }
        }
    }
    
    function ProposeDriver(address payable _driverAddress, uint _salary) public onlyManager returns(string memory){
        driver = driverStruct(_driverAddress,_salary, 0, block.timestamp, 0, 0, state.Waiting);
        return "Driver has been going voting by participant.";
    }
    
    function SetDriver() public onlyManager returns(string memory){
        if((driver.falseVoteCount + driver.trueVoteCount == participantCount) && (driver.falseVoteCount > ((uint) (participantCount / 2))))
        {
            return "Voting has been end and offer rejected by participants. Arrange new driver.";
        }else{
            if(driver.trueVoteCount < ((uint)(participantCount / 2)))
            {
                return "There are not enough approve vote.";
            }else{
                driver.approvalState = state.Approval;
                driver.lastPayDate = block.timestamp;
            }
        }
    }
    
    function FireDriver() public onlyManager returns(string memory){
        if(driver.approvalState != state.Approval){
            companyBalance -= driver.salary;
            driver.balance += driver.salary;
            driver.approvalState = state.Fired;
        }else{
            return "There is no approved driver.";
        }
    }
    
    function ReleaseSalary() public onlyManager returns(string memory){
        if(driver.approvalState != state.Approval){
            if(block.timestamp - driver.lastPayDate >= oneMonthTime){
                companyBalance -= driver.salary;
                driver.balance += driver.salary;
                driver.lastPayDate = block.timestamp;
            }else{
                return "Company has been pay drivers salary in this month.";
            }
        }else{
            return "There is no approved driver.";
        }
    }
    
    function PayCarExpenses() public onlyManager payable returns(string memory){
        if(block.timestamp - lastExpensesTime >= sixMonthTime){
            companyBalance -= fixedExpenses;
            carDealer.transfer(fixedExpenses);
            return "You have been pay car expenses.";
        }else{
            return "You have already been pay car expenses in 6 month.";
        }
    }
    
    function PayDividend() public onlyManager payable  returns(string memory){
        if(block.timestamp - lastProfitShareTime >= sixMonthTime){
            if(block.timestamp - lastExpensesTime >= sixMonthTime){
                companyBalance -= fixedExpenses;
                carDealer.transfer(fixedExpenses);
            }
            
            if(block.timestamp - driver.lastPayDate >= oneMonthTime){
                companyBalance -= driver.salary;
                driver.balance += driver.salary;
                driver.lastPayDate = block.timestamp;
            }
            
            uint256 diviedPay = companyBalance / participantCount;
            for(uint256 i=0; i < participantCount; i++){
                Participants[participantsAddress[i]].balance += diviedPay;
            }
        }else{
            return "Company has been share profit in six month.";
        }
    }
    
    function CarProposeToBusiness(uint256 _carId, uint256 _offerValidTime, uint256 _price) public onlyCarDealer returns(string memory){
        proposedCar = car(_carId, _offerValidTime, 0, 0, _price, state.Waiting);
        return "Proposed has been setting on system. Please wait voting.";
    }
    
    function RepurchaseCarPropose(uint256 _carId, uint256 _offerValidTime, uint256 _price) public onlyCarDealer returns(string memory){
        proposedRepurchase = car(_carId, _offerValidTime, 0, 0, _price, state.Waiting);
        return "Proposed has been setting on system. Please wait voting.";
    }
    
    function Repurchasecar() public onlyCarDealer payable returns(string memory){
        if((proposedRepurchase.falseVoteCount + proposedRepurchase.trueVoteCount == participantCount) && (proposedRepurchase.falseVoteCount > ((uint) (participantCount / 2))))
        {
            return "Voting has been end and offer rejected by participants";
        }else{
            if(proposedRepurchase.trueVoteCount < ((uint)(participantCount / 2)))
            {
                return "There are not enough approve vote.";
            }else{
                if(proposedRepurchase.offerValidTime == 0)
                {
                    return "There is no proposed car. Arrange new proposel.";
                }else{
                    if(proposedRepurchase.offerValidTime < block.timestamp)
                    {
                        return "Offers valid time has been passed. Arrange new proposel.";
                    }else{
                        ownedCarId = 0;
                        companyBalance += proposedRepurchase.price;
                        manager.transfer(proposedRepurchase.price);
                    }
                }
            }
        }
    }
    
    function ApproveSellProposal(bool _vote) public onlyParticipants returns(string memory){
        if(Participants[msg.sender].isGiveVoteForCar == 0){
            Participants[msg.sender].isGiveVoteForCar = 1;
            if(_vote == true){
                proposedRepurchase.trueVoteCount += 1;
            }else{
                proposedRepurchase.falseVoteCount += 1;
            }
            return "You have been vote for current sell proposal";
        }else{
            return "You have already been vote for current sell proposal";
        }
    }
    
    function ApprovePurchaseCar(bool _vote) public onlyParticipants returns(string memory){
        if(Participants[msg.sender].isGiveVoteForCar == 0){
            Participants[msg.sender].isGiveVoteForCar = 1;
            if(_vote == true){
                proposedCar.trueVoteCount += 1;
            }else{
                proposedCar.falseVoteCount += 1;
            }
            return "You have been vote for current buying proposal";
        }else{
            return "You have already been vote for current buying proposal";
        }
    }
    
    function ApproveDriver(bool _vote) public onlyParticipants returns(string memory){
        if(Participants[msg.sender].isGiveVoteForDriver == 0){
            Participants[msg.sender].isGiveVoteForDriver = 1;
            if(_vote == true){
                driver.trueVoteCount += 1;
            }else{
                driver.falseVoteCount += 1;
            }
            return "You have been vote for current driver proposal";
        }else{
            return "You have already been vote for current driver proposal";
        }
    }
    
    function GetDividend() public onlyParticipants returns(string memory){
        if(Participants[msg.sender].balance != 0){
            Participants[msg.sender].identity.transfer(Participants[msg.sender].balance);
            Participants[msg.sender].balance = 0;
            return "The money in your balance is transferred to your account";
        }else{
            return "You have no money in your account.";
        }
    }
    
    function GetSalary() public onlyDriver returns(string memory){
        if(driver.balance != 0 && driver.approvalState == state.Approval){
            driver.identity.transfer(driver.balance);
            driver.balance = 0;
            return "The money in your balance is transferred to your account";
        }else{
            return "You have no money in your account or you have not been approved.";
        }
    }
}
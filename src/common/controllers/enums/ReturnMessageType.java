package common.controllers.enums;
/**
 * enum class that present all the message types that the obl can return 
 */
public enum ReturnMessageType {
	UserIsNotExist, 
	UserSuccessLogin, 
	UserFailedLogin,
	BooksFound,
	BooksNotFound,
	SubscriberExist,
	SubscriberNotExist,
	EmailOrPhoneAreAlreadyExists,
	BookWasNotFoundOnManageStock,
	BooksFoundOnManageStock, 
	Successful,
	Unsuccessful, 
	SuccessfulWithLastSnapshotDate,
	ErrorWhileTyping,
	ClientIsAlreadyLogin,
	SubscriberIsLocked, 
	CopyNotExist,
	HoldOrLockStatus, 
	CopyIsNotAvailable, 
	SubscriberAlreadyInOrderList, 
	wrongBorrowDetails,
	FullOrderList,
	ChangeStatusToLock,
	ChangeStatusToActive, 
	subscriberInWaitingList, 
	GetStatstic,
	BookHaveWaitingList,
	SubscriberStatusNotActive,
	MustReturnBook,
	PopularBook,
	GraduateWithMoreBooksToReturn,

	ChangeGraduateStatusToLock, 
	CopyIsAlreadyExist,

	CheckSubscriberStatus,
	
}
